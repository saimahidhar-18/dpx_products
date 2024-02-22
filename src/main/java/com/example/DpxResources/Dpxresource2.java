package com.example.DpxResources;

import java.util.List;

import javax.annotation.security.RolesAllowed;
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
import com.example.Filters.CredentialFilter;
import com.mongodb.MongoException;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

@Path("/data_products")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class Dpxresource2 {


    Dpxservice1 dpxservice1=new Dpxservice1();
    // private final CredentialFilter credfilter;

    // public Dpxresource2(CredentialFilter credfilter){
    //     this.credfilter=credfilter;
    // }

    @GET
    public Response getProducts(){
        try{
            List<Product> products = dpxservice1.getAllProducts();
            if(products.size()==0)
                return Response.ok("products collection is empty").build();
            else
                return Response.ok(products).build();
        }
        catch (MongoException e) {
            
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error getting the products.").build();
        }
        
    }
    
    @POST
   // @RolesAllowed("producer")
    public Response addProduct(Product product){
        try{
            Product p = dpxservice1.addProduct(product);
            return Response.ok(p).build();
        }
        catch (MongoException e) {
            
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error deleting the product.").build();
        }
    }


    @GET
    @Path("/{productid}")
    public Response getProduct(@PathParam("productid") long id){
        try{
            Product product = dpxservice1.getProduct(id);
            if (product != null) 
                return Response.ok(product).build();
            else 
                return Response.status(Response.Status.NOT_FOUND).entity("The Product id is invalid!").build();
            
        }
        catch (MongoException e) {
            
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error in adding the product.").build();
        }
    }

    @PUT
    @Path("/{productId}")
    public Response updateProduct (@PathParam("productId") long id, Product product) {
        product.setId(id);
        try{
            UpdateResult result = dpxservice1.updateProduct(product);
            if(result.wasAcknowledged()){
                if(result.getMatchedCount()==0)
                    return Response.status(Response.Status.NOT_FOUND).entity("The Product id is invalid!").build();
                else 
                    return Response.ok(product).build();
            }
            else{
                return Response.status(Response.Status.NOT_MODIFIED).entity("Server couldn't acknowledge the update operation.").build();
            }
        }
        catch (MongoException e) {
            
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error updatinging the product.").build();
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
