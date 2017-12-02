package com.horacio.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;


/**
 * Created by Horac on 2017/5/15.
 */
@Entity
public class Record {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String phone;
    private String itemName;
    private Date borrowedTime ;
    private Date returnTime ;
    private String borrowOperator;
    private String returnOperator;
    private int number;

    public String getBorrowOperator() {
        return borrowOperator;
    }

    public void setBorrowOperator(String borrowOperator) {
        this.borrowOperator = borrowOperator;
    }

    public String getReturnOperator() {
        return returnOperator;
    }

    public void setReturnOperator(String returnOperator) {
        this.returnOperator = returnOperator;
    }

    public Record() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Date getBorrowedTime() {
        return borrowedTime;
    }

    public void setBorrowedTime(Date borrowedTime) {
        this.borrowedTime = borrowedTime;
    }

    public Date getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(Date returnTime) {
        this.returnTime = returnTime;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
