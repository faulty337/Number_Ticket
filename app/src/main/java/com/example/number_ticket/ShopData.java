package com.example.number_ticket;

public class ShopData {
    private String name;
    private String type;
    private String tel_number;
    private String address;

    public ShopData(String name, String tel_number, String type, String address){
        this.name = name;
        this.tel_number = tel_number;
        this.type = type;
        this.address = address;
    }

    public String getName()
    {
        return this.name;
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