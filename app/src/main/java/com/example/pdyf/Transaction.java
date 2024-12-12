package com.example.pdyf;

import java.util.Date;

public class Transaction {
    private int id;
    private int categoryId;
    private double amount;
    private String date;
    private String type;


    // Конструторы класса
    public Transaction() {
    }

    public Transaction(int id, int categoryId, double amount, String date, String type) {
        this.id = id;
        this.categoryId = categoryId;
        this.amount = amount;
        this.date = date;
        this.type = type;
    }

    public Transaction(int categoryId, double amount, String date, String type) {
        this.categoryId = categoryId;
        this.amount = amount;
        this.date = date;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String  getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public String getCategoryName(){
        int id = this.id;

        return "Одежда";

    }
}
