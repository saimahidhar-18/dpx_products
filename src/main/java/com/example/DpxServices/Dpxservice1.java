package com.example.DpxServices;


import java.util.*;
import java.util.stream.Collectors;

import org.bson.Document;
import org.bson.types.ObjectId;
import com.example.DpxModel.Product;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;


public class Dpxservice1 {
    
    

    private static final String CONNECTION_STRING = "mongodb://localhost:27017";
    MongoClient mongoClient = MongoClients.create(CONNECTION_STRING);
    MongoDatabase database = mongoClient.getDatabase("data_products");
    MongoCollection<Document> collection = database.getCollection("products");

    public Dpxservice1(){
        if(collection.countDocuments()==0){
            Document prod1 = new Document("id",111L)
            .append("name", "pd1")
            .append("description", "for use")
            .append("domain", "Education")
            .append("date", new Date())
            .append("status", "published")
            .append("author", "harry")
            .append("dataList", Arrays.asList("url1", "url2"))
            .append("users", Arrays.asList("Harry","Ron"));

            Document prod2 = new Document("id",112L)
            .append("name", "pd2")
            .append("description", "for use")
            .append("domain", "Education")
            .append("date", new Date())
            .append("status", "published")
            .append("author","Harry")
            .append("dataList", Arrays.asList("url1", "url2"))
            .append("users", Arrays.asList("Harry","Hermoine"));

            Document prod3 = new Document("id",113L)
            .append("name", "pd3")
            .append("description", "for use")
            .append("domain", "Education")
            .append("date", new Date())
            .append("status", "published")
            .append("author", "Harry")
            .append("dataList", Arrays.asList("url1", "url2"))
            .append("users", Arrays.asList("Harry","Ron","Hermoine"));

            collection.insertOne(prod1);
            collection.insertOne(prod2);
            collection.insertOne(prod3);
        }
    }

    public List<Product> getAllProducts(){
        
        FindIterable<Document> findIterable = collection.find();
        // Getting the iterator
        Iterator<Document> iterator = findIterable.iterator();
        List<Product> list=new ArrayList<>();
        while (iterator.hasNext()){
            Document document = iterator.next();

            long id = document.getLong("id");
            String name = document.getString("name");
            String desc = document.getString("description");
            String domain = document.getString("domain");
            String status = document.getString("status");
            String author = document.getString("author");
            List<String> users =document.getList("users", String.class);
            Product m1=new Product(id,name,desc, domain, status,author,users);
            list.add(m1);
        }

        System.out.println(list.size());
        return list;
    }
    public Product getProduct(long id){

        Document document = collection.find(Filters.eq("id", id)).first();

        if(document!=null){
            String name = document.getString("name");
            String description = document.getString("description");
            String domain = document.getString("domain");
            String status = document.getString("status");
            String author = document.getString("author");
            //List<String> urls = document.getList("urls", String.class);
            List<String> users =document.getList("users", String.class);
            //List<Document> urls = document.getList("urls", Document.class);



            Product P = new Product(id,name,description,domain,status,author,users);
            return P;
        }
        return null;


        
    }

    public Product addProduct(Product product){
        
            product.setId((long)collection.countDocuments()+111);
    
            Document document = new Document("id", product.getId())
                    .append("name", product.getName())
                    .append("domain", product.getDomain())
                    .append("status", product.getStatus())
                    .append("description", product.getDescription());
                    // .append("urls", product.getUrls())
                    // .append("users", product.getUsers());
            collection.insertOne(document);
            System.out.println("Document inserted successfully.");

            return product;
    }


    public UpdateResult updateProduct(Product product){
        if(product.getId()<=0) return null;

        UpdateResult result = collection.updateOne(
            Filters.eq("id", product.getId()),
            Updates.combine(
                Updates.set("name", product.getName()),
                Updates.set("description", product.getDescription()),
                Updates.set("domain", product.getDomain()),
                Updates.set("status", product.getStatus()),
                Updates.set("users", product.getUsers())
               // Updates.set("urls", product.getUrls())
                
            )
        );

        return result;

    }


    public DeleteResult removeProduct(long id){
            
       
        Document document = collection.find(Filters.eq("id", id)).first();
        if(document!=null){
            List<String> users =document.getList("users", String.class);
            if(users.size()<2){
                DeleteResult result = collection.deleteOne(Filters.eq("id",id)); 
                return result;
            }
        }
        return null;
        
    }
    
}
