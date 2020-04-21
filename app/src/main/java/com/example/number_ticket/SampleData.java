package com.example.number_ticket;

public class SampleData {
    private String SName;
    private String SGroup;
    private String TelNumber;
    private String Saddr;

    public SampleData(String SName, String TelNumber, String SGroup, String Saddr){
        this.SName = SName;
        this.TelNumber = TelNumber;
        this.SGroup = SGroup;
        this.Saddr = Saddr;
    }

    public String getSName()
    {
        return this.SName;
    }

    public String getSGroup()
    {
        return this.SGroup;
    }

    public String getTelNumber()
    {
        return this.TelNumber;
    }

    public String getSaddr()
    {
        return this.Saddr;
    }
}