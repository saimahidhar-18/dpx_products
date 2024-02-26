package com.example.DpxModel;

import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Product {
    private long id;
    private String name;
    private String description;
    private String domain;
    private Date date;
    private String status;
    private String author;
   // private List<String> urls;
    // private List<DataList> dataLists;
    // private List<String> users;

    public Product(){
    }
    
    public Product(long id, String name, String description, String domain, String status,
            String author) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.domain = domain;
        this.date = new Date();
        this.status = status;
       
        // this.dataLists=dataLists;
        // this.users= users;
    }
    
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String Author) {
        this.author = Author;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String Name) {
        this.name = Name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String Description) {
        this.description = Description;
    }
    public String getDomain() {
        return domain;
    }
    public void setDomain(String Domain) {
        this.domain = Domain;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String Status) {
        this.status = Status;
    }
    // public List<String> getUsers() {
    //     return users;
    // }
    // public void setUsers(List<String> users) {
    //     this.users = users;
    // }
    // public List<DataList> getDataList() {
    //     return dataLists;
    // }
    // public void setDataList(List<DataList> dataLists) {
    //     this.dataLists = dataLists;
    // }
}