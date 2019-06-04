package com.yummy.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order {
    int oid;
    String serial;
    int mid;
    int uid;//会员id
    String time;
    double total;
    int situation;
    int beforeSituation;
    Set<GoodItem> goodItems;
    Set<Coupon> coupons;
    double packageCost;
    double conveyCost;
    Address address;
    String backInfo;
    String details;

    public Order(){}

    @Column(name = "serial")
    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    @Column(name = "oid")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    @Column(name = "mid")
    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    @Column(name = "uid")
    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    @Column(name = "time")
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Column(name = "total")
    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Column(name = "situation")
    public int getSituation() {
        return situation;
    }

    public void setSituation(int situation) {
        this.situation = situation;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    public Set<GoodItem> getGoodItems() {
        return goodItems;
    }

    public void setGoodItems(Set<GoodItem> goodItems) {
        this.goodItems = goodItems;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    public Set<Coupon> getCoupons() {
        return coupons;
    }

    public void setCoupons(Set<Coupon> coupons) {
        this.coupons = coupons;
    }

    @Column(name = "package_cost")
    public double getPackageCost() {
        return packageCost;
    }

    public void setPackageCost(double packageCost) {
        this.packageCost = packageCost;
    }

    @Column(name = "convey_cost")
    public double getConveyCost() {
        return conveyCost;
    }

    public void setConveyCost(double conveyCost) {
        this.conveyCost = conveyCost;
    }

    @ManyToOne
    @JoinColumn(name = "address")
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Column(name = "back_info")
    public String getBackInfo() {
        return backInfo;
    }

    public void setBackInfo(String backInfo) {
        this.backInfo = backInfo;
    }

    @Column(name = "details")
    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Column(name = "before_situation")
    public int getBeforeSituation() {
        return beforeSituation;
    }

    public void setBeforeSituation(int beforeSituation) {
        this.beforeSituation = beforeSituation;
    }
}
