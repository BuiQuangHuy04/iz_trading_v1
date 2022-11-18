package com.huybui.iztradingv1.Model;

import java.io.Serializable;

public class Order implements Serializable {

    protected String ticket;
    protected String pair;
    protected String type;
    protected String lots;
    protected String price;
    protected String sl;
    protected String tp;
    protected String time;
    protected String comment;

    public Order() {
    }

    public String getTicket() {
        return ticket;
    }

    public String getPair() {
        return pair;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public String getSl() {
        return sl;
    }

    public String getTp() {
        return tp;
    }

    public String getTime() {
        return time;
    }

    public String getComment() {
        return comment;
    }

    @Override
    public String toString() {
        return "Order{" +
                "ticket='" + ticket + '\'' +
                ", pair='" + pair + '\'' +
                ", type='" + type + '\'' +
                ", lots='" + lots + '\'' +
                ", price='" + price + '\'' +
                ", sl='" + sl + '\'' +
                ", tp='" + tp + '\'' +
                ", time='" + time + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
