package com.app.myapplication.util;


import java.util.ArrayList;
import java.util.List;

public class CartData {
    private static CartData instance;

    private List<String> cart;

    private CartData(){
        cart = new ArrayList<>();
    }

    public static CartData getInstance(){
        if(instance == null){
            instance = new CartData();
        }
        return instance;
    }

    public void add(String s){
        cart.add(s);
    }

    public List<String> get(){
        return cart;
    }
}
