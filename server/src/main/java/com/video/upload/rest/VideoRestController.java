package com.video.upload.rest;

import com.video.upload.rest.models.CombineFileModel;
import com.video.upload.rest.models.FileHashModel;
import com.video.upload.service.CryptoService;
import com.video.upload.service.FilesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.video.common.CommonUtils.*;

/**
 * @author Ihar Zharykau
 */
@RestController
public class VideoRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(VideoRestController.class);

    private FilesService filesService;
    private CryptoService cryptoService;

    @Autowired
    public VideoRestController(FilesService filesService, CryptoService cryptoService) {
        this.filesService = filesService;
        this.cryptoService = cryptoService;
    }

    @GetMapping("/test")
    public Map<String, String> test() {
        Map<String, String> result = new HashMap<>();
        result.put("test", "test");
        result.put("test1", "test2");
        return result;
    }

    @PostMapping(value = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map upload(@RequestBody FileHashModel fileHashModel) {
        byte[] fileBytes;
        try {
            fileBytes = fileHashModel.getFile().getBytes();
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            return RESULT_ERROR;
        }
        String realHash = cryptoService.hash(fileBytes);
        boolean success = false;
        if (realHash.equals(fileHashModel.getHash())) {
            success = filesService.uploadPartFile(realHash, fileBytes);
        }
        return success ? RESULT_SUCCESS : RESULT_ERROR;
    }

    @PostMapping("/check-single-part-exists")
    public Map fileExists(@RequestBody Map<String, String> body) {
        return filesService.partFileExists(body.get("hashOfFile")) ? RESULT_SUCCESS : RESULT_ERROR;
    }

    @PostMapping("/check-parts-exists")
    public Map partsExists(@RequestBody List<String> hashes) {
        List<String> exists = new ArrayList<>();
        List<String> notExists = new ArrayList<>();
        hashes.forEach(hash -> {
            if (filesService.partFileExists(hash)) {
                exists.add(hash);
            } else {
                notExists.add(hash);
            }
        });
        return mapOf("exists", exists, "notExists", notExists);
    }

    @PostMapping("/combine")
    public Map combinePartsToFile(@RequestBody CombineFileModel combineFileModel){
        try {
            filesService.combinePartsToFile(combineFileModel.getHashes(), combineFileModel.getFileName());
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            return RESULT_ERROR;
        }
        return RESULT_SUCCESS;
    }

    @GetMapping("/list-all-files")
    public List<String> listAllFiles(){
        return filesService.listAllFiles();
    }

    @GetMapping("/list-all-parts")
    public List<String> listAllParts(){
        return filesService.listAllParts();
    }
}
