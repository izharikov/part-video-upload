package com.video.upload.rest.models;

import java.util.List;

/**
 * @author Ihar Zharykau
 */
public class CombineFileModel {
    private List<String> hashes;
    private String fileName;

    public List<String> getHashes() {
        return hashes;
    }

    public void setHashes(List<String> hashes) {
        this.hashes = hashes;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
