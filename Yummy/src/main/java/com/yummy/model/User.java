package com.yummy.model;

import com.yummy.util.Score;
import org.springframework.util.Base64Utils;
import sun.misc.BASE64Encoder;

import javax.persistence.*;
import java.awt.*;
import java.io.*;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {
    private int uid;
    private String name;
    String email;
    String password;
    String phone;
    private String image;
    double balance;

    int level;
    int score;
    int points;
    int nextLevelScore;

    private Set<Coupon> coupons;
    Set<Address> addresses;
    Set<ChargeRecord> chargeRecords;
    Cart cart;
    int nowAddress;

    public User(String email, String password){
        this.uid =1;
        this.email = email;
        this.password = password;
        this.name = this.email;
        BASE64Encoder encoder = new BASE64Encoder();
        File file = new File("src/main/resources/image.txt");
        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            this.image = br.readLine();
            System.out.println(this.image);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public User(){
        this.nextLevelScore = Score.getNextScore(this.score);
    }

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="uid",nullable=true)
    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    public Set<Coupon> getCoupons() {
        return coupons;
    }

    public void setCoupons(Set<Coupon> coupons) {
        this.coupons = coupons;
    }

    @Column(name = "image")
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @ManyToOne
    @JoinColumn(name = "cart", unique = true)
    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    public Set<ChargeRecord> getChargeRecords() {
        return chargeRecords;
    }

    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setChargeRecords(Set<ChargeRecord> chargeRecords) {
        this.chargeRecords = chargeRecords;
    }

    @Column(name = "now_address")
    public int getNowAddress() {
        return nowAddress;
    }

    public void setNowAddress(int nowAddress) {
        this.nowAddress = nowAddress;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    @Column(name = "score")
    public int getScore() {
        return score;
    }

    @Column(name = "points")
    public int getPoints() {
        return points;
    }

    @Column(name = "next_level_score")
    public int getNextLevelScore() {
        return nextLevelScore;
    }

    @Column(name = "level")
    public int getLevel() {
        return level;
    }

    @Column(name = "balance")
    public double getBalance() {
        return balance;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setScore(int score) {
        this.score = score;
        this.level = Score.getLevel(score);
        this.nextLevelScore = Score.getNextScore(this.score);

    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setNextLevelScore(int nextLevelScore) {
        this.nextLevelScore = nextLevelScore;
    }
}
