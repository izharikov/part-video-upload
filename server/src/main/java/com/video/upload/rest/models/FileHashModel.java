package com.video.upload.rest.models;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author Ihar Zharikau
 */
public class FileHashModel {
    private MultipartFile file;
    private String hash;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
