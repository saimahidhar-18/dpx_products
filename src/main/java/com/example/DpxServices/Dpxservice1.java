package com.example.DpxServices;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.example.Databases.Databaseclass;
import com.example.DpxModel.Product;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import org.bson.types.ObjectId;

public class Dpxservice1 {
    

    private static final String CONNECTION_STRING = "mongodb://localhost:27017";
    MongoClient mongoClient = MongoClients.create(CONNECTION_STRING);
    MongoDatabase database = mongoClient.getDatabase("data_products");
    MongoCollection<Document> collection = database.getCollection("products");

    //private static Map<Long,Product> products =Databaseclass.getProducts();

    public List<Product> getAllProducts(){
        
        FindIterable<Document> findIterable = collection.find();
        // Getting the iterator
        Iterator<Document> iterator = findIterable.iterator();
        List<Product> list=new ArrayList<>();
        while (iterator.hasNext()){
            Document document = iterator.next();

            long id=(long)document.get("id");
            String name=document.get("name").toString();
            String author=document.get("author").toString();
            Product m1=new Product(id,name,null, null, null,author, null);
            list.add(m1);
        }

        System.out.println(list.size());
        return list;
    }
    public Product getProduct(long id){

        // FindIterable<Document> findIterable = collection.find();
        // Iterator<Document> iterator = findIterable.iterator();
        // while (iterator.hasNext()){
        //     Document document = iterator.next();
        //     long test=id;
        //     if(test==(long)document.get("id")){
        //         //=(long)document.get("id");
        //         String name=document.get("name").toString();
        //         String author=document.get("author").toString();
        //         Product p1=new Product(test,name,null, null, null,author, null);
        //         //System.out.println(p1.getAuthor() + " product author");

        //         return p1;
        //     }
        // }
        // return null;

//-----------------------------------------------------------------------------------------

        Document document = collection.find(Filters.eq("id", id)).first();

        if(document!=null){
            String name = document.getString("name");
            String description = document.getString("description");
            String domain = document.getString("domain");
            String status = document.getString("status");
            String author = document.getString("author");
            //List<Document> urls = document.getList("urls", Document.class);



            Product P = new Product(id,name,description,domain,status,author,null);
            return P;
        }
        return null;
        
    }

    public Product addProduct(Product product){
        
            product.setId((long)collection.countDocuments()+100+1);
    
            Document document = new Document("id", product.getId())
                    .append("name", product.getName())
                    .append("author", product.getAuthor());
            collection.insertOne(document);
            System.out.println("Document inserted successfully.");

            return product;
    }


    public UpdateResult updateProduct(Product product){
        if(product.getId()<=0) return null;

        UpdateResult result = collection.updateOne(Filters.eq("id",(Object)product.getId()),Updates.set("name",(Object)product.getName()));
        System.out.println("Document updated successfully.");

        return result;


//---------------------------------------------------------------------------------------
        //**Update For all fields**

        // UpdateResult result = collection.updateOne(
        //     Filters.eq("id", product.getId()),
        //     Updates.combine(
        //         Updates.set("name", product.getName()),
        //         Updates.set("description", product.getDescription()),
        //         Updates.set("domain", product.getDomain()),
        //         Updates.set("status", product.getStatus()),
        //         Updates.set("author", product.getAuthor()),
        //         Updates.set("urls", product.getUrls())
        //         // Add more updates for other fields as needed
        //     )
        // );

        // return result;
    }


    public DeleteResult removeProduct(long id){
            
        // FindIterable<Document> findIterable = collection.find();
        // // Getting the iterator
        // Iterator<Document> iterator = findIterable.iterator();      
        // while (iterator.hasNext()){
        //     Document document = iterator.next();
        //     long test=id;
        //     if(test==(long)document.get("id")){
        //         DeleteResult result =collection.deleteOne(document);
        //         return result;
                
        //     }
           
        // }
        // return null;
//-----------------------------------------------------------------------------

        DeleteResult result = collection.deleteOne(Filters.eq("id",id));
        return result;
    }
    
}
