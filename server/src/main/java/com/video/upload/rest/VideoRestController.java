package com.video.upload.rest;

import com.video.upload.service.CryptoService;
import com.video.upload.service.FilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.video.common.CommonUtils.*;

/**
 * @author Ihar Zharykau
 */
@RestController
public class VideoRestController {

    private FilesService filesService;
    private CryptoService cryptoService;

    @Autowired
    public VideoRestController(FilesService filesService, CryptoService cryptoService) {
        this.filesService = filesService;
        this.cryptoService = cryptoService;
    }

    @GetMapping("/test")
    public Map<String, String> test(){
        Map<String,String> result = new HashMap<>();
        result.put("test","test");
        result.put("test1","test2");
        return result;
    }

    @PostMapping("/upload")
    public Map upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam("expectedHash") String expectedHash){
        byte[] fileBytes;
        try {
            fileBytes = file.getBytes();
        } catch (IOException e){
            return RESULT_ERROR;
        }
        String realHash = cryptoService.hash(fileBytes);
        boolean success = false;
        if ( realHash.equals(expectedHash)){
            success = filesService.uploadPartFile(realHash, fileBytes);
        }
        return success ? RESULT_SUCCESS : RESULT_ERROR;
    }

    @GetMapping("/check-file-exists")
    public Map fileExists(@RequestParam("hashOfFile") String hashOfFile){
        return filesService.partFileExists(hashOfFile) ? RESULT_SUCCESS : RESULT_ERROR;
    }
}
