package com.example.DpxResources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/")
public class DataListResource {
    
    @GET
    public String test() {
    return "new sub resource";
    }
}
