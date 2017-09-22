package com.video.upload.rest;

import com.video.common.CommonUtils;
import com.video.upload.rest.models.CombineFileModel;
import com.video.upload.service.CryptoService;
import com.video.upload.service.FilesService;
import com.video.upload.service.StreamingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    private StreamingService streamingService;

    @Autowired
    public VideoRestController(
            FilesService filesService,
            CryptoService cryptoService,
            StreamingService streamingService) {
        this.filesService = filesService;
        this.cryptoService = cryptoService;
        this.streamingService = streamingService;
    }

    @GetMapping("/test")
    public Map<String, String> test() {
        Map<String, String> result = new HashMap<>();
        result.put("test", "test");
        result.put("test1", "test2");
        return result;
    }

    @PostMapping("/upload")
    public Map upload(
            @RequestParam MultipartFile file,
            @RequestParam String expectedHash) {
        byte[] fileBytes;
        try {
            fileBytes = file.getBytes();
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            return RESULT_ERROR;
        }
        String realHash = cryptoService.hash(fileBytes);
        boolean success = false;
        if (realHash.equals(expectedHash)) {
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

    @GetMapping("/video/start")
    public Map startStream(@RequestParam("videoFile") String videoFile){
        streamingService.startStreaming(videoFile);
        return mapOf("url", "http://localhost/hls/" + CommonUtils.removeSymbols(videoFile) + ".m3u8");
    }
}
