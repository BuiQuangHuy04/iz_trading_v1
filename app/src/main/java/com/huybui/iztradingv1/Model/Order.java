package com.huybui.iztradingv1.Model;

public class Order {
    public String ticket;
    public String pair;
    public String type;
    public String lots;
    public String price;
    public String sl;
    public String tp;
    public String time;
    public String comment;

    public Order() {
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getPair() {
        return pair;
    }

    public void setPair(String pair) {
        this.pair = pair;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLots() {
        return lots;
    }

    public void setLots(String lots) {
        this.lots = lots;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSl() {
        return sl;
    }

    public void setSl(String sl) {
        this.sl = sl;
    }

    public String getTp() {
        return tp;
    }

    public void setTp(String tp) {
        this.tp = tp;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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
