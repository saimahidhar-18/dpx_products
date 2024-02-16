package com.example.DpxResources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.example.DpxModel.Product;
import com.example.DpxServices.Dpxservice1;

@Path("/data_products")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class Dpxresource2 {
    Dpxservice1 dpxservice1=new Dpxservice1();

    @GET
    public List<Product> getProducts(){
        return dpxservice1.getAllProducts();
    }
    
    @POST
    public Product addProduct(Product product){
        return dpxservice1.addProduct(product);
    }


    @GET
    @Path("/{productid}")
    public Product test(@PathParam("productid") long id){
        return dpxservice1.getProduct(id);
    }

    @PUT
    @Path("/{productId}")
    public Product updateProduct (@PathParam("productId") long id, Product product) {
        product.setId(id);
        return dpxservice1.updateProduct(product);
    }
    @DELETE
    @Path("/{productId}")
    public void deleteMessage(@PathParam("productId") long id) {
        dpxservice1.removeProduct(id);
    }

}
