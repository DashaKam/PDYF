package com.example.pdyf.TransactionManager.Categories;

public class Category {

        private int id;
        private String name;
        private double totalSpent;

    public Category(String name) {
        this.name = name;
    }

    public Category() {
    }

    public Category(int id, String name, double totalSpent) {
        this.id = id;
        this.name = name;
        this.totalSpent = totalSpent;
    }
    public Category(String name, double totalSpent) {
        this.name = name;
        this.totalSpent = totalSpent;
    }

    public String getName() {
        return name;
    }

    public double getTotalSpent() {
        return totalSpent;
    }

    public void setTotalSpent(double totalSpent) {
        this.totalSpent = totalSpent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
