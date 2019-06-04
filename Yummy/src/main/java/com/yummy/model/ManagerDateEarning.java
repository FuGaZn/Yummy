package com.yummy.model;

import javax.persistence.*;

@Entity
@Table(name = "manager_date_earning")
public class ManagerDateEarning {
    int id;
    String date;
    double earning;
    public ManagerDateEarning(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "date")
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Column(name = "earning")
    public double getEarning() {
        return earning;
    }

    public void setEarning(double earning) {
        this.earning = earning;
    }
}
