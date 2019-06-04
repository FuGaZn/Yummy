package com.yummy.model;

import javax.persistence.*;

/**
 * 商品
 */

@Entity
@Table(name = "good")
public class Good {
    int gid;
    String mid;
    String name;
    double price;
    int bought_number;
    int max_number;
    String details;

    String image;

    public Good(){}

    @Id
    @Column(name = "gid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    @Column(name = "mid")
    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
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

    @Column(name = "image")
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Column(name = "details")
    public String getDetails() {
        return details;
    }

    @Column(name = "max_number")
    public int getMax_number() {
        return max_number;
    }

    @Column(name = "bought_number")
    public int getBought_number() {
        return bought_number;
    }

    public void setBought_number(int bought_number) {
        this.bought_number = bought_number;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setMax_number(int max_number) {
        this.max_number = max_number;
    }
}
