package com.horacio.Model;

import javax.persistence.*;
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
    private String email;
    private String itemName;
    private Date borrowedTime ;
    private Date returnTime ;

    @OneToOne
    @JoinColumn(name = "borrowOperator")
    private Admin borrowOperator;

    @OneToOne
    @JoinColumn(name = "returnOperator")
    private Admin returnOperator;

    private int number;
    private int type;// 1 内部使用 2借出

    public Admin getBorrowOperator() {
        return borrowOperator;
    }

    public void setBorrowOperator(Admin borrowOperator) {
        this.borrowOperator = borrowOperator;
    }

    public Admin getReturnOperator() {
        return returnOperator;
    }

    public void setReturnOperator(Admin returnOperator) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
