package com.example.number_ticket;

import androidx.annotation.NonNull;

public class ShopData {
    private String name;
    private String type;
    private String tel_number;
    private String address;
    private String code;
    private Boolean code_use;
    private String owner;
    private Boolean use;
    private int waitnumber;
    

    public ShopData(String name, String tel_number, String type, String address){
        this.name = name;
        this.tel_number = tel_number;
        this.type = type;
        this.address = address;
    }



    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
    public void setWaitnumber(int waitnumber){
        this.waitnumber = waitnumber;
    }
    public void setow(String owner) {
        this.owner = owner;
    }
    public void setOwner(String owner) {
        this.owner = owner;
    }
    public void setCode_use(Boolean code_use) {
        this.code_use = code_use;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getName()
    {
        return this.name;
    }
    public int getWaitnumber(){
        return this.waitnumber;
    }
    public String getType()
    {
        return this.type;
    }
    public String getTel_number()
    {
        return this.tel_number;
    }
    public String getAddress()
    {
        return this.address;
    }
}