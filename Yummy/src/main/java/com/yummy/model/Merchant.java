package com.yummy.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "merchant")
public class Merchant {
    int mid;
    String name;
    String serial;
    String password;
    String phone;
    String image;

    int type;
    double conveyCost;  //配送费
    double packageCost; //包装费
    double balance;
    double todayEarning;
    String todayDate;

    String briefIntroduction;   //简介
    String bulletin; //公告
    boolean hasCertification;   //是否有证书

    Address address;
    Set<Coupon> coupons;
    Set<Good> goods;
    Set<Package> packages;

    public Merchant(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mid")
    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "serial")
    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name = "image")
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Column(name = "type")
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Column(name = "convey_cost")
    public double getConveyCost() {
        return conveyCost;
    }

    public void setConveyCost(double conveyCost) {
        this.conveyCost = conveyCost;
    }

    @Column(name = "package_cost")
    public double getPackageCost() {
        return packageCost;
    }

    public void setPackageCost(double packageCost) {
        this.packageCost = packageCost;
    }

    @Column(name = "introduction")
    public String getBriefIntroduction() {
        return briefIntroduction;
    }

    public void setBriefIntroduction(String briefIntroduction) {
        this.briefIntroduction = briefIntroduction;
    }

    @Column(name = "bulletin")
    public String getBulletin() {
        return bulletin;
    }

    public void setBulletin(String bulletin) {
        this.bulletin = bulletin;
    }

    @Column(name = "certification")
    public boolean isHasCertification() {
        return hasCertification;
    }

    public void setHasCertification(boolean hasCertification) {
        this.hasCertification = hasCertification;
    }

    @ManyToOne
    @JoinColumn(name = "address")
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "merchant_id")
    public Set<Coupon> getCoupons() {
        return coupons;
    }

    public void setCoupons(Set<Coupon> coupons) {
        this.coupons = coupons;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "merchant_id")
    public Set<Good> getGoods() {
        return goods;
    }

    public void setGoods(Set<Good> goods) {
        this.goods = goods;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "merchant_id")
    public Set<Package> getPackages() {
        return packages;
    }

    public void setPackages(Set<Package> packages) {
        this.packages = packages;
    }

    @Column(name = "balance")
    public double getBalance() {
        return balance;
    }

    @Column(name = "todayEarning")
    public double getTodayEarning() {
        return todayEarning;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setTodayEarning(double todayEarning) {
        this.todayEarning = todayEarning;
    }

    @Column(name = "today_date")
    public String getTodayDate() {
        return todayDate;
    }

    public void setTodayDate(String todayDate) {
        this.todayDate = todayDate;
    }
}
