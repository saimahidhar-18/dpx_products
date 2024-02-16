package com.example.Databases;

import java.util.HashMap;
import java.util.Map;


import com.example.DpxModel.Product;



public class Databaseclass {

    private static Map<Long, Product>products=new HashMap<>();

    public static Map<Long, Product> getProducts(){
        return products;
    }
}
