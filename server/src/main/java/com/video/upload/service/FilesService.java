package com.video.upload.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;

/**
 * @author Ihar Zharykau
 */
@Component
public class FilesService {

    private ServiceConfiguration serviceConfiguration;

    @Autowired
    public FilesService(ServiceConfiguration serviceConfiguration) throws NoSuchAlgorithmException {
        this.serviceConfiguration = serviceConfiguration;
    }

    public boolean uploadPartFile(String fileName, byte[] file){
        try{
            OutputStream wr = new FileOutputStream(serviceConfiguration.getUploadDir() + fileName);
            wr.write(file);
            wr.close();
        } catch (IOException e){
            return false;
        }
        return true;
    }

    public boolean partFileExists(String fileName){
        File file = new File(serviceConfiguration.getUploadDir() + fileName);
        return file.exists();
    }
}
