package com.horacio.Model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
@Entity
public class Item {
    @Id
    @GeneratedValue
    private int id;
    private String itemName;
    private int itemQTY;
    @Column( length = 100000 )
    private String url;
    private int date;

    public Item() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemQTY() {
        return itemQTY;
    }

    public void setItemQTY(int itemQTY) {
        this.itemQTY = itemQTY;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }
}
