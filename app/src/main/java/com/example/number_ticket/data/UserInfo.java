package com.example.number_ticket.data;

import android.os.health.UidHealthStats;

import java.util.List;

public class UserInfo {
    private String name;
    private String id;
    private String phoneNumber;
    private String shop;

    public UserInfo(String name, String id, String phoneNumber){
        this.name = name;
        this.id = id;
        this.phoneNumber = phoneNumber;
    }
    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getPhoneNumber(){
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public String getid(){
        return this.id;
    }

    public void setid(String id){
        this.id = id;
    }

    public String getShop(){
        return this.shop;
    }
    public void setShop(String shop){
        this.shop = shop;
    }

}
