package com.video;

import com.video.upload.JaxRsController;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

/**
 * @author Ihar Zharykau
 */
@Component
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(JaxRsController.class);
    }

}