package com.example.DpxModel;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserCredentials {
    
    private String username;
    private String password;
    private String role;

    public UserCredentials(){

    }

    public UserCredentials(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    //hellooooo
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    

    


}
