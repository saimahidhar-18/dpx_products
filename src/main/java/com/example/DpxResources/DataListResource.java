package com.example.DpxResources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.example.DpxModel.Product;

@Path("/")
public class DataListResource {
    
    @GET
    public List<Product> getAllDatalist(@PathParam("productid") long id) {
    return new ArrayList<>();
    }
}
