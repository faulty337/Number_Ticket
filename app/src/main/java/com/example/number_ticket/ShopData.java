package com.example.number_ticket;

public class ShopData {
    private String name;
    private String type;
    private String tel_number;
    private String address;
    private String code;
    private Boolean code_use;
    private int waitnumber;
    private String owner;
    private Boolean use;

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

    public String getCode(){ return this.code; }

    public Boolean getCode_use(){ return this.code_use; }

    public void setWaitnumber(int waitnumber) {
        this.waitnumber = waitnumber;
    }

    public int getWaitnumber() {
        return waitnumber;
    }

    public void setUse(Boolean use) {
        this.use = use;
    }

    public Boolean getUse() {
        return use;
    }
}