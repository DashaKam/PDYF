package com.example.pdyf.Goals;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Goal {
    private int id;
    private String name;
    private double monthlyPayment;

    private String period;

    private double sum;

    public Goal(String name, double monthlyPayment, String period, double sum) {
        this.name = name;
        this.monthlyPayment = monthlyPayment;
        this.period = period;
        this.sum = sum;
    }

    public Goal(int id, String name, double monthlyPayment, String period, double sum) {
        this.id = id;
        this.name = name;
        this.monthlyPayment = monthlyPayment;
        this.period = period;
        this.sum = sum;
    }
    public Goal(String name,String period, double sum) {

        this.name = name;
        this.period = period;
        this.sum = sum;
    }

    public Goal() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMonthlyPayment(double monthlyPayment) {
        this.monthlyPayment = monthlyPayment;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getMonthlyPayment() {
        return monthlyPayment;
    }

    public String getPeriod() {
        return period;
    }

    public double getSum() {
        return sum;
    }
    public double countMonthPayment(){
        return this.sum/Integer.parseInt(this.period);
    }
    public double countMonthPayment(double sum, String period){
        return BigDecimal.valueOf(sum).divide(BigDecimal.valueOf(Integer.parseInt(period)), 3, RoundingMode.UP).doubleValue();

    }
}
