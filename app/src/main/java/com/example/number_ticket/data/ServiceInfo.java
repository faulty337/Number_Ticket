package com.example.number_ticket.data;

public class ServiceInfo {
    private String service;
    private int time;

    public ServiceInfo(String service, int time){
        this.service = service;
        this.time = time;
    }

    public int getTime() {
        return time;
    }

    public String getService() {
        return service;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setService(String service) {
        this.service = service;
    }

}
