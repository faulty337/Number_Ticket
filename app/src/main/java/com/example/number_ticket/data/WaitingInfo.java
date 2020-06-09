package com.example.number_ticket.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class WaitingInfo {
    private int ticket_number;
    private String time;
    private int waiting_number;
    private String waitingtime;
    private String email;
    private String name;
    private String shopname;
    private Boolean onoff;
    private ArrayList<String> menu = new ArrayList<>();
    private int Service_total;
    private int personnel;

    public WaitingInfo(int ticket_number, String time, String waitingtime, int waiting_number, String shopname) {
        super();
        this.ticket_number = ticket_number;
        this.time = time;
        this.waitingtime = waitingtime;
        this.waiting_number = waiting_number;
        this.shopname = shopname;
        this.onoff = false;
        this.Service_total = 0;
        this.personnel = 1;
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

    public ArrayList<String> getMenu() {
        return menu;
    }

    public int getService_total() {
        return Service_total;
    }

    public int getPersonnel() {
        return personnel;
    }

    public void setPersonnel(int personnel) {
        this.personnel = personnel;
    }

    public void setService_total(int service_total) {
        Service_total = service_total;
    }

    public void setMenu(ArrayList<String> menu) {
        this.menu = menu;
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

