package com.example.number_ticket.data;

public class ServiceInfo {
    private String service;
    private String time;

    public ServiceInfo(String service, String time){
        this.service = service;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public String getService() {
        return service;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setService(String service) {
        this.service = service;
    }

}
