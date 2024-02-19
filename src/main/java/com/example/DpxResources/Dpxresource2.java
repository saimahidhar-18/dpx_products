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
import javax.ws.rs.core.Response;

import com.example.DpxModel.Product;
import com.example.DpxServices.Dpxservice1;
import com.mongodb.MongoException;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

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
    public Response getProduct(@PathParam("productid") long id){
        Product product = dpxservice1.getProduct(id);
        if (product != null) {
            // If the product is found, return it
            return Response.ok(product).build();
        } else {
            // If the product is not found, return a response with no content (204 No Content)
            return Response.status(Response.Status.NOT_FOUND).entity("The Product id is invalid!").build();
        }
    }

    @PUT
    @Path("/{productId}")
    public Response updateProduct (@PathParam("productId") long id, Product product) {
        product.setId(id);
        UpdateResult result = dpxservice1.updateProduct(product);
        if(result.wasAcknowledged()){
            if(result.getMatchedCount()==0){
                return Response.status(Response.Status.NOT_FOUND).entity("The Product id is invalid!").build();

            }
            else return Response.ok(product).build();
        }
        else{
            return Response.status(Response.Status.NOT_MODIFIED).entity("Server couldn't acknowledge the update operation.").build();
        }
    }

    
    @DELETE
    @Path("/{productId}")
    public Response deleteMessage(@PathParam("productId") long id) {
        try {
            DeleteResult result = dpxservice1.removeProduct(id);
            if (result.getDeletedCount() == 0) 
                return Response.status(Response.Status.NOT_FOUND).entity("The Product id is invalid!").build();             
            else 
                return Response.ok("Deletion successful.").build();            
        } 
        catch (MongoException e) {
            
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error deleting the product.").build();
        }

    }

}
