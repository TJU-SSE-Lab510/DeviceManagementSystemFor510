package com.horacio.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 发送邮件线程
 */
@Component
public class SendThread implements Runnable {

    @Autowired
    private EmailUtil email;

    private Thread thread;
    private String destination;
    private String name;
    private String itemName;
    private String borrowDate;
    private String image;
    private int number;
    private int month;


    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setBorrowDate(String borrowDate) {
        this.borrowDate = borrowDate;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public void run() {
        try{
            email.send(destination,name,itemName,borrowDate,image,number,month);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start(){
        thread = new Thread(this);
        thread.start ();
    }
}
