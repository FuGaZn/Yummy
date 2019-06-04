package com.yummy.model;


import javax.persistence.*;
import java.util.Date;

/**
 * 会员充值记录
 */
@Entity
@Table(name = "charge_record")
public class ChargeRecord {
    String time;
    int crid;
    int uid;
    double chargeMoney;
    double balance;

    public ChargeRecord() {
    }

    public ChargeRecord(String time, int crid, double chargeMoney, double balance) {
        this.time = time;
        this.crid = crid;
        this.chargeMoney = chargeMoney;
        this.balance = balance;
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

    public void setCrid(int crid) {
        this.crid = crid;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "crid")
    public int getCrid() {
        return crid;
    }

    @Column(name = "charge_money")
    public double getChargeMoney() {
        return chargeMoney;
    }

    public void setChargeMoney(double chargeMoney) {
        this.chargeMoney = chargeMoney;
    }

    @Column(name = "balance")
    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
