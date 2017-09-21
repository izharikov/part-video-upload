package com.video.upload.service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Ihar Zharykau
 */
@ConfigurationProperties(
        prefix = "streaming.service"
)
@Component
public class StreamingConfiguration {

    private String ffmpegCommand;
    private String hlsVideosPath;

    public String getFfmpegCommand() {
        return ffmpegCommand;
    }

    public void setFfmpegCommand(String ffmpegCommand) {
        this.ffmpegCommand = ffmpegCommand;
    }

    public String getHlsVideosPath() {
        return hlsVideosPath;
    }

    public void setHlsVideosPath(String hlsVideosPath) {
        this.hlsVideosPath = hlsVideosPath;
    }
}
