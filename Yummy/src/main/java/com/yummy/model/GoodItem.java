package com.yummy.model;

import javax.persistence.*;

/**
 * 商品
 */

@Entity
@Table(name = "good_item")
public class GoodItem {
    int good_item_id;
    int gid;
    int pid;
    int mid;
    String name;
    double price;
    int number;
    double total;
    String details;
    String image;

    public GoodItem(){}

    @Column(name = "gid")
    public int getGid() {
        return gid;
    }

    @Column(name = "details")
    public String getDetails() {
        return details;
    }

    @Id
    @Column(name = "good_item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getGood_item_id() {
        return good_item_id;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public void setGood_item_id(int good_item_id) {
        this.good_item_id = good_item_id;
    }

    @Column(name = "pid")
    public int getPid() {
        return pid;
    }

    @Column(name = "mid")
    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "price")
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Column(name = "number")
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Column(name = "total")
    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Column(name = "image")
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
