package com.yummy.model;

import javax.persistence.*;

@Entity
@Table(name = "manager")
public class Manager {
    int manager_id;
    String name;
    String email;
    String password;
    double balance;
    double todayEarning;
    String todayDate;

    public Manager(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manager_id")
    public int getManager_id() {
        return manager_id;
    }

    public void setManager_id(int manager_id) {
        this.manager_id = manager_id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "balance")
    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Column(name = "today_date")
    public String getTodayDate() {
        return todayDate;
    }

    public void setTodayDate(String todayDate) {
        this.todayDate = todayDate;
    }

    @Column(name = "today_earning")
    public double getTodayEarning() {
        return todayEarning;
    }

    public void setTodayEarning(double todayEarning) {
        this.todayEarning = todayEarning;
    }
}
