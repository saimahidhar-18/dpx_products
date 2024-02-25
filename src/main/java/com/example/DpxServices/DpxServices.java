package com.example.DpxServices;


import java.util.*;
import java.util.stream.Collectors;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.example.DpxModel.DataList;
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


public class DpxServices {
    
    

    
    MongoClient mongoClient = Dbconnector.getclient();
    MongoDatabase database = mongoClient.getDatabase("data_products");
    MongoCollection<Document> collection = database.getCollection("products");


    public DpxServices(){
        if(collection.countDocuments()==0){
            Document prod1 = new Document("id",111L)
            .append("name", "Historical weather data API - Meteosource")
            .append("description", "Historical weather data for any location worldwide and observed hourly data for the requested day.The Historical Weather API allows you to quickly retrieve accurate, high resolution historical weather data for any location in the world. Our curated weather data is backed by over 120,000 weather stations as well as high resolution gridded weather datasets such as the ERA-5 re-analysis, and global satellite / doppler radar.")
            .append("domain", "weather data")
            .append("date", new Date())
            .append("status", "published")
            .append("producer", "Jack Sparrow")
            .append("dataList", Arrays.asList(new Document("urlId",201L),
            new Document("urlName", "dp1dl1"),new Document("urlDescription","iiiiiiiii"),
            new Document("creationDate","2003"),new Document("lastUpdateDate", "2023"),
            new Document("copyUrl","https://www.google.co.in/")))
            .append("users", Arrays.asList("Harry","Ron"));

            Document prod2 = new Document("id",112L)
            .append("name", "Customised Weather Data Validation")
            .append("description", "-Customised solutions for validation, scoring, post-processing, calibration and data cleansing of weather data products.\r\n" + //
                                "WeatherLogistics has 10-years experience in climate data science. Its previous validation solutions include an inter-comparison assessment of numerical weather prediction models, assessment of meteorological data against soil moisture measurements, and pioneering research and development of a seasonal climate forecast system. More recently, its team developed software to score GFS and ECMWF products on a decentralised climate data marketplace.")
            .append("domain", "weather data")
            .append("date", new Date())
            .append("status", "published")
            .append("producer","Jack Sparrow")
            .append("dataList", Arrays.asList(new Document("urlId",202L),
            new Document("urlName", "dp2dl1"),new Document("urlDescription","jjjjjjjjj"),
            new Document("creationDate","2003"),new Document("lastUpdateDate", "2023"),
            new Document("copyUrl","https://www.google.co.in/")))
            .append("users", Arrays.asList("Harry","Hermoine"));

            Document prod3 = new Document("id",113L)
            .append("name", "Weather Source: OnPoint Weather Impact Indices")
            .append("description", "Weather Source's Weather Impact Indices incorporate numerous factors such as climatology to provide a reference for the weather being above or below normal, recent weather trends, and even weather forecasts.\r\n" + //
                                "Consumer and Business Weather Impact Indices are aggregations of weather and climate data that are engineered to correlate to numerous consumer and business activities.")
            .append("domain", "weather data")
            .append("date", new Date())
            .append("status", "published")
            .append("producer", "Jack Sparrow")
            .append("dataList", Arrays.asList(new Document("urlId",203L),
            new Document("urlName", "dp3dl1"),new Document("urlDescription","kkkkkkkkkkk"),
            new Document("creationDate","2003"),new Document("lastUpdateDate", "2023"),
            new Document("copyUrl","https://www.google.co.in/")))
            .append("users", Arrays.asList("Harry","Ron","Hermoine"));

            Document prod4 = new Document("id",113L)
            .append("name", "LIVE Daily Weather Feed | United States Weather Data")
            .append("description", "AWIS Weather Services houses, maintains, and constantly updates an extensive database full of Climate and Weather Data. We offer a LIVE Weather Data Feed that is updated daily. Real United States Weather Observations, checked for quality and reliability. Sorted by Zip Code.")
            .append("domain", "weather data")
            .append("date", new Date())
            .append("status", "published")
            .append("producer", "Jack Sparrow")
            .append("dataList", Arrays.asList(new Document("urlId",204L),
            new Document("urlName", "dp4dl1"),new Document("urlDescription","llllllllllll"),
            new Document("creationDate","2003"),new Document("lastUpdateDate", "2023"),
            new Document("copyUrl","https://www.google.co.in/")))
            .append("users", Arrays.asList("Harry","Ron","Hermoine"));

            collection.insertOne(prod1);
            collection.insertOne(prod2);
            collection.insertOne(prod3);
            collection.insertOne(prod4);

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
            List<DataList> dataList = document.getList("dataList",DataList.class);
            Product m1=new Product(id,name,desc, domain, status,author,dataList,users);
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
            List<DataList> dataList=document.getList("dataList",DataList.class);
            List<String> users =document.getList("users", String.class);
            



            Product P = new Product(id,name,description,domain,status,author,dataList,users);
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
                    .append("description", product.getDescription())
                    .append("dataList", product.getDataList())
                    .append("users", product.getUsers());
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
                Updates.set("users", product.getUsers()),
                Updates.set("dataLists", product.getDataList())
                
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
    public DeleteResult deleteAll(){
            
       
        DeleteResult result = collection.deleteMany(new Document());
        return result;
        
    }
    
}
