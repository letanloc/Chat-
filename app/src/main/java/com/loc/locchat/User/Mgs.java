package com.loc.locchat.User;

import java.util.Date;

/**
 * Created by loc on 05/12/2015.
 */
public class Mgs {

//    private  String

    public static int Danggui = 0;
    public static int Dagui = 1;
    public static int ThatBai = 2;
    private int status = Danggui;
    private Date date;
    private String msg;
    private String Sender;


    public Mgs() {


    }

    public Mgs(int status, Date date, String msg, String sender) {
        this.status = status;
        this.date = date;
        this.msg = msg;
        Sender = sender;
    }

    public boolean isSent() {
        return List.user.getUsername().equals(Sender);
    }

    public static int getDanggui() {
        return Danggui;
    }

    public static void setDanggui(int danggui) {
        Danggui = danggui;
    }

    public static int getDagui() {
        return Dagui;
    }

    public static void setDagui(int dagui) {
        Dagui = dagui;
    }

    public static int getThatBai() {
        return ThatBai;
    }

    public static void setThatBai(int thatBai) {
        ThatBai = thatBai;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSender() {
        return Sender;
    }

    public void setSender(String sender) {
        Sender = sender;
    }
}
