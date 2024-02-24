package com.example.Filters;


import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import com.example.resources.CredentialResource;
import com.example.services.CredentialServices;

import java.io.IOException;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class AuthenticationFilter implements ContainerRequestFilter {

    CredentialServices credentialService = CredentialResource.credentialServices; 

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String path = requestContext.getUriInfo().getPath();

        String[] segments = path.split("/");

        if (segments.length > 0 && "login".equals(segments[segments.length - 1])) {
            return;
        }
        
        String username = "Harry"; 
       // if(credentialService==null) return;
        if (!credentialService.isUserAuthorized(username)) {

            requestContext.abortWith(Response.status(Response.Status.FORBIDDEN)
                    .entity("{\"error\": \"User is not authorized\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build());
        }
        return;

    }
}
