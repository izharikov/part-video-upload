package com.video.upload.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ihar Zharykau
 */
@Component
public class FilesService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FilesService.class);

    private ServiceConfiguration serviceConfiguration;

    @Autowired
    public FilesService(ServiceConfiguration serviceConfiguration) throws NoSuchAlgorithmException {
        this.serviceConfiguration = serviceConfiguration;
    }

    public boolean uploadPartFile(String fileName, byte[] file) {
        try {
            OutputStream wr = new FileOutputStream(serviceConfiguration.getUploadDir() + fileName);
            wr.write(file);
            wr.close();
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    public boolean partFileExists(String fileName) {
        File file = new File(serviceConfiguration.getUploadDir() + fileName);
        return file.exists();
    }

    public void combinePartsToFile(List<String> hashes, String fileName) throws IOException {
        try (OutputStream wr = new FileOutputStream(serviceConfiguration.getSaveDir() + fileName)) {
            for ( String hash : hashes){
                wr.write(readSavedPart(hash));
            }
            wr.close();
        }
    }

    private byte[] readSavedPart(String partName) throws IOException{
        InputStream ip = new FileInputStream(serviceConfiguration.getUploadDir() + partName);
        byte[] result = new byte[ip.available()];
        ip.read(result);
        ip.close();
        return result;
    }

    public List<String> listAllFiles(){
        return filesFromDir(serviceConfiguration.getSaveDir());
    }

    public List<String> listAllParts(){
        return filesFromDir(serviceConfiguration.getUploadDir());
    }

    private List<String> filesFromDir(String dirName){
        List<String> result = new ArrayList<>();
        File path = new File(dirName);
        File[] files = path.listFiles();
        if ( files == null ){
            return result;
        }
        for ( File f : files){
            if ( f != null && f.isFile() && !f.isHidden()){
                result.add(f.getName());
            }
        }
        return result;
    }
}
