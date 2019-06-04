package com.yummy.model;

import javax.naming.Name;
import javax.persistence.*;
import java.util.Set;
import java.util.Set;

/**
 * 套餐
 */
@Entity
@Table(name = "package")
public class Package {
    int pid;
    String mid;
    String name;
    double setting_price;
    double real_price;
    int max_number;
    int bought_number;
    double total;
    Set<GoodItem> goods;
    String image;
    String details;

    public int getGoodItemNumber(int id){
        for (GoodItem g:goods){
            if (g.getGood_item_id() == id){
                return g.getNumber();
            }
        }
        return 0;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pid")
    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
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

    @Column(name = "real_price")
    public double getReal_price() {
        return real_price;
    }

    @Column(name = "details")
    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Column(name = "setting_price")
    public double getSetting_price() {
        return setting_price;
    }

    public void setReal_price(double real_price) {
        this.real_price = real_price;
    }

    public void setSetting_price(double setting_price) {
        this.setting_price = setting_price;
    }

    @Column(name = "bought_number")
    public int getBought_number() {
        return bought_number;
    }

    @Column(name = "max_number")
    public int getMax_number() {
        return max_number;
    }

    public void setBought_number(int bought_number) {
        this.bought_number = bought_number;
    }

    public void setMax_number(int max_number) {
        this.max_number = max_number;
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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "package_id")
    public Set<GoodItem> getGoods() {
        return goods;
    }

    public void setGoods(Set<GoodItem> goods) {
        this.goods = goods;
    }
}
