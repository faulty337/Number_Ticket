package com.example.number_ticket.data;
public class waitingInfo {
    private int ticket_number;
    private String time;
    private int waiting_number;
    private String waitingtime;

    public waitingInfo(int ticket_number, String time, String waitingtime, int waiting_number) {
        super();
        this.ticket_number = ticket_number;
        this.time = time;
        this.waitingtime = waitingtime;
        this.waiting_number = waiting_number;
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

    public void setTicket_number(int present_number) {
        this.ticket_number = present_number;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setWaitingtime(String waitingtime) {
        this.waitingtime = waitingtime;
    }
}

