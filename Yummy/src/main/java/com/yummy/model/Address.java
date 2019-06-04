package com.yummy.model;

import javax.persistence.*;

@Entity
@Table(name = "address")
public class Address {
    int aid;
    int uid;
    int mid;
    String province_code;
    String province;
    String city_code;
    String city;
    String part_code;
    String part;
    String details;
    String name;
    int gender;
    String phone;

    public Address(){

    }
    public Address(int aid, int uid, int mid, String province_code, String province, String city_code, String city, String part_code, String part, String details, String name, int gender, String phone) {
        this.aid = aid;
        this.uid = uid;
        this.mid = mid;
        this.province_code = province_code;
        this.province = province;
        this.city_code = city_code;
        this.city = city;
        this.part_code = part_code;
        this.part = part;
        this.details = details;
        this.name = name;
        this.gender = gender;
        this.phone = phone;
    }

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

    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    @Column(name = "uid")
    public int getUid() {
        return uid;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "aid")
    public int getAid() {
        return aid;
    }

    @Column(name = "gender")
    public int getGender() {
        return gender;
    }

    @Column(name = "city")
    public String getCity() {
        return city;
    }

    @Column(name = "city_code")
    public String getCity_code() {
        return city_code;
    }

    @Column(name = "details")
    public String getDetails() {
        return details;
    }

    @Column(name = "part")
    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }

    @Column(name = "part_code")
    public String getPart_code() {
        return part_code;
    }

    @Column(name = "province")
    public String getProvince() {
        return province;
    }

    @Column(name = "province_code")
    public String getProvince_code() {
        return province_code;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCity_code(String city_code) {
        this.city_code = city_code;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public void setPart_code(String part_code) {
        this.part_code = part_code;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setProvince_code(String province_code) {
        this.province_code = province_code;
    }

    @Override
    public String toString() {
        return "aid="+aid+" uid="+uid+" province="+province+" name="+name+" gender="+gender+" phone="+phone;
    }
}
