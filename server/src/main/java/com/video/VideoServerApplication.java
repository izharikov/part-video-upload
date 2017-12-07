package com.video;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author Ihar Zharykau
 */
@SpringBootApplication
public class VideoServerApplication extends SpringBootServletInitializer {
    public static void main(String... args){
        new VideoServerApplication()
                .configure(new SpringApplicationBuilder(VideoServerApplication.class))
                .run(args);
    }
}
