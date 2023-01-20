package com.example.demo.model;

public class MoneyConverter {

    private double price; // price in $;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double convertToEuros(double price) {
        return price * 0.92;
    }

    public double convertToRon(double price) {
        return price * 4.56;
    }

}
