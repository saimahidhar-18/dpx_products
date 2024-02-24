package com.example.resources;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

//import org.bson.json.JsonObject;

import com.example.services.CredentialServices;

@Path("/login")

public class CredentialResource {
    
    public static CredentialServices credentialServices = new CredentialServices();


    @GET
    public String test(){
        return "working!!!!!";
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)

    public JsonObject login(String body){

        //username=user&password=pass

        String[] params = body.split("&");
        String username = params[0].split("=")[1];
        String password = params[1].split("=")[1];

        
        if(credentialServices.isValid(username, password)){
            String userRole = credentialServices.getUserRole(username);
            credentialServices.setState(username);
            JsonObject jsonResponse = Json.createObjectBuilder()
                .add("status", "success")
                .add("userRole", userRole)
                .build();
            return jsonResponse;

        }
        else {
            // Handle invalid credentials
            JsonObject errorResponse = Json.createObjectBuilder()
                    .add("status", "error")
                    .add("message", "Invalid credentials")
                    .build();
            return errorResponse;
        }

    }




}
