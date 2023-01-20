package com.example.demo.exceptions;

public class NoMoneyError extends RuntimeException {

    public NoMoneyError() {
        super("Not enough money to purchase the Album!");
    }
}
