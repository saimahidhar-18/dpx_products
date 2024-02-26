package com.example.DpxServices;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bson.Document;

import com.example.DpxServices.Dbconnector;
import com.example.DpxModel.DataList;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class DataListServices {
    MongoClient mongoClient= Dbconnector.getclient();
    MongoDatabase database = mongoClient.getDatabase("data_products");
    MongoCollection<Document> collection = database.getCollection("products");

    public DataListServices(){

    }

    public List<DataList> getDataLists(){
        FindIterable<Document> findIterable = collection.find();
        // Getting the iterator
        Iterator<Document> iterator = findIterable.iterator();
        while (iterator.hasNext()){
            Document document = iterator.next();

            long id = document.getLong("id");
            String name = document.getString("name");
            String desc = document.getString("description");
            String domain = document.getString("domain");
            String author = document.getString("author");
            //List<DataList> datalist= document.getList("dataList", DataList.class);
            
        }
        return new ArrayList<>();
    }
}
