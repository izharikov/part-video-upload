package com.video.upload;

import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Map;

import static com.video.common.CommonUtils.RESULT_SUCCESS;

/**
 * @author Ihar Zharykau
 */
@Component
@Path("/jax-rs")
@Produces(MediaType.APPLICATION_JSON)
public class JaxRsController {

    @Path("/test")
    @GET
    public Map test(){
        return RESULT_SUCCESS;
    }
}
