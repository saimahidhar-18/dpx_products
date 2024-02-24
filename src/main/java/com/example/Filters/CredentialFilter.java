package com.example.Filters;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import javax.annotation.Priority;
// import javax.inject.Singleton;
// import javax.ws.rs.Path;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
//import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
//import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

import com.example.services.CredentialServices;

@Provider
@Priority(Priorities.AUTHENTICATION)
// @Path("/filter-resource")
// @Singleton
public class CredentialFilter implements ContainerResponseFilter{

    public final CredentialServices credentialServices = new CredentialServices();



    private static final String headerKey = "Authorization";
    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        
        
        List<String> authHead = requestContext.getHeaders().get(headerKey);
        if(authHead!=null)
        {
            String authToken = authHead.get(0);
            authToken = authToken.replace("Basic ", "");
            String decodedString = new String(Base64.getDecoder().decode(authToken));
            String[] arr = decodedString.split(":");
            String username = arr[0];
            String password = arr[1];

            if(credentialServices.isValid(username, password)){
                String userRole = credentialServices.getUserRole(username);


               
                MultivaluedMap<String, Object> headers = responseContext.getHeaders();
                headers.add("UserRole", userRole);   
                
                return ;
            }
        }

        Response unauthorizedStatus = Response.status(Response.Status.UNAUTHORIZED).entity("User cannot access the resource").build();

        requestContext.abortWith(unauthorizedStatus);
        //return;

    }
    
}
