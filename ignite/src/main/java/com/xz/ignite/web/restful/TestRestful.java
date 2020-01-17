package com.xz.ignite.web.restful;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by Administrator on 2020/1/16.
 */
@Path("/test")
public class TestRestful {

    @Path("get") // url上没有参数，参数通过body传入
    @GET
    @Produces(value = MediaType.APPLICATION_JSON)
    @Consumes(value = MediaType.APPLICATION_FORM_URLENCODED)
    public String get(@QueryParam("key") String key) {
        return key;
    }
}
