package com.example.number_ticket.data;
public class WaitingInfo {
    private int ticket_number;
    private String time;
    private int waiting_number;
    private String waitingtime;
    private String email;
    private String name;
    private String shopname;
    private Boolean onoff;

    public WaitingInfo(int ticket_number, String time, String waitingtime, int waiting_number, String shopname) {
        super();
        this.ticket_number = ticket_number;
        this.time = time;
        this.waitingtime = waitingtime;
        this.waiting_number = waiting_number;
        this.shopname = shopname;
        this.onoff = false;
    }

    public String getShopname() {
        return shopname;
    }

    public String getEmail() {
        return email;
    }

    public int getTicket_number() {
        return ticket_number;
    }

    public String getTime() {
        return time;
    }

    public String getWaitingtime() {
        return waitingtime;
    }

    public int getWaiting_number() {
        return waiting_number;
    }

    public String getName() {
        return name;
    }

    public Boolean getOnoff() {
        return onoff;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public void setTicket_number(int present_number) {
        this.ticket_number = present_number;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setWaitingtime(String waitingtime) {
        this.waitingtime = waitingtime;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setWaiting_number(int waiting_number) {
        this.waiting_number = waiting_number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOnoff(Boolean onoff) {
        this.onoff = onoff;
    }
}

