package com.video.upload.service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Ihar Zharykau
 */
@ConfigurationProperties(
        prefix = "video.service"
)
@Component
public class ServiceConfiguration {
    private String uploadDir;
    private String saveDir;
    private String hashMethod;

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }

    public String getSaveDir() {
        return saveDir;
    }

    public void setSaveDir(String saveDir) {
        this.saveDir = saveDir;
    }

    public String getHashMethod() {
        return hashMethod;
    }

    public void setHashMethod(String hashMethod) {
        this.hashMethod = hashMethod;
    }
}
