package com.video.upload.service;

import com.video.common.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;

/**
 * @author Ihar Zharykau
 */
@Component
public class StreamingService {
    private StreamingConfiguration streamingConfiguration;
    private ServiceConfiguration serviceConfiguration;

    @Autowired
    public StreamingService(StreamingConfiguration streamingConfiguration,
                            ServiceConfiguration serviceConfiguration) {
        this.streamingConfiguration = streamingConfiguration;
        this.serviceConfiguration = serviceConfiguration;
    }

    public void startStreaming(String videoFile) {
        String streamFileName = CommonUtils.removeSymbols(videoFile);
        File file = new File(streamingConfiguration.getHlsVideosPath() + streamFileName + ".m3u8");
        if (file.exists()) {
            return;
        }

        String fileName = new File(serviceConfiguration.getSaveDir() + videoFile).getAbsolutePath();
        new Thread(() -> {
            Process process = null;
            try {
                String cmdline = MessageFormat.format(streamingConfiguration.getFfmpegCommand(), fileName, streamFileName);
                process =
                        new ProcessBuilder(new String[] {"bash", "-c", cmdline})
                                .start();
                System.out.println("Started streaming of " + fileName);
//                process.waitFor();
                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(process.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = "";
                while ((line = reader.readLine())!= null) {
                    sb.append(line + "\n");
                }
                process.waitFor();
                System.out.println("[OUTPUT] " + sb.toString());
                System.out.println("Ended streaming of " + fileName);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

    }
}
