package com.example.number_ticket.data;

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

    public ShopData(String name, String tel_number, String type, String address, String code, Boolean code_use, String owner){
        this.name = name;
        this.tel_number = tel_number;
        this.type = type;
        this.address = address;
        this.code = code;
        this.code_use = code_use;
        this.waitnumber = 0;
        this.owner = owner;
        this.use = false;
    }


    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
    public void setWaitnumber(int waitnumber){
        this.waitnumber = waitnumber;
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

    public String getCode(){ return this.code; }

    public Boolean getCode_use(){ return this.code_use; }
    

    public void setUse(Boolean use) {
        this.use = use;
    }

    public Boolean getUse() {
        return use;
    }
}