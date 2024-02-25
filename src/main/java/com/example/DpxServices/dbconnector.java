package com.example.DpxServices;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

public class Dbconnector {
    private static final String CONNECTION_STRING = "mongodb://localhost:27017";
    private static MongoClient client;

    
        
    
    public static MongoClient getclient() {
        if(client==null){
            client = MongoClients.create(CONNECTION_STRING);
        }
        return client;
    }
    
}
