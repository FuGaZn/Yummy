package com.yummy.model;

import javax.persistence.*;

/**
 * 交由经理审查的商家信息
 */
@Entity
@Table(name = "approval_merchant")
public class ApprovalMerchant {
    int amid;
    boolean approval;
    boolean refused;

    String name;
    int mid;
    String image;
    Address address;
    int type;

    String phone;

    String briefIntroduction;   //简介
    String bulletin; //公告

    public ApprovalMerchant() {
    }

    @Id
    @Column(name = "amid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getAmid() {
        return amid;
    }

    public void setAmid(int amid) {
        this.amid = amid;
    }

    @Column(name = "approval")
    public boolean isApproval() {
        return approval;
    }

    public void setApproval(boolean approval) {
        this.approval = approval;
    }

    @Column(name = "refused")
    public boolean isRefused() {
        return refused;
    }

    public void setRefused(boolean refused) {
        this.refused = refused;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "mid")
    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    @Column(name = "image")
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @ManyToOne
    @JoinColumn(name = "address")
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    @Column(name = "type")
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
