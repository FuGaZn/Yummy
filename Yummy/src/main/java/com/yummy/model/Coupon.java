package com.yummy.model;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "coupon")
public class Coupon {
    int cid;
    int mid;
    int uid;
    double discount;
    double satisfied;
    String start;
    String end;
    boolean disabled;


    public Coupon(){

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cid")
    public int getCid() {
        return cid;
    }

    @Column(name = "end")
    public String getEnd() {
        return end;
    }

    @Column(name = "start")
    public String getStart() {
        return start;
    }

    @Column(name = "discount")
    public double getDiscount() {
        return discount;
    }

    @Column(name = "satisfied")
    public double getSatisfied() {
        return satisfied;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public void setSatisfied(double satisfied) {
        this.satisfied = satisfied;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    @Column(name = "disabled")
    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }
}
