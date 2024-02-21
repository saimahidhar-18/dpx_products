package com.example.DpxServices;


import org.bson.Document;

//import org.apache.commons.codec.digest.DigestUtils;
import org.mindrot.jbcrypt.BCrypt;

import com.example.DpxModel.UserCredentials;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class CredentialServices {

    private static final String CONNECTION_STRING = "mongodb://localhost:27017";
    MongoClient mongoClient = MongoClients.create(CONNECTION_STRING);
    MongoDatabase database = mongoClient.getDatabase("data_products");
    MongoCollection<Document> credCollection = database.getCollection("credentials");

    UserCredentials user0 = new UserCredentials("Ginny",BCrypt.hashpw("pass123", BCrypt.gensalt()),"consumer");

    

    public CredentialServices(){
        if (credCollection.countDocuments() == 0) {
            Document user1 = new Document("username","Harry")
            .append("password", BCrypt.hashpw("pass123", BCrypt.gensalt()))
            .append("role", "producer");

            Document user2 = new Document("username","Ron")
            .append("password", BCrypt.hashpw("pass1234", BCrypt.gensalt()))
            .append("role", "consumer");

            Document user3 = new Document("username","Hermoine")
            .append("password", BCrypt.hashpw("password123", BCrypt.gensalt()))
            .append("role", "consumer");


            credCollection.insertOne(user1);
            credCollection.insertOne(user2);
            credCollection.insertOne(user3);
        }
        
    }

    public boolean isValid(String username, String password){
        FindIterable<Document> documents = credCollection.find(Filters.eq("userName", username));
        MongoCursor<Document> cursor = documents.iterator();
        while(cursor.hasNext()){
            Document doc = cursor.next();

            String hashedPassword = doc.getString("password");
            return BCrypt.hashpw(password, BCrypt.gensalt()).equals(hashedPassword);
        }
        return true;
    }

    public UserCredentials addUser(UserCredentials newUser){
        Document user = new Document("username",newUser.getUsername())
        .append("password", BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt()))
        .append("role", newUser.getRole());

        credCollection.insertOne(user);
        return newUser;
    }
    
    public String getUserRole(String username){
        Document document = credCollection.find(Filters.eq("username", username)).first();
        String role = document.getString("role");
        return role;
    }

    
}
