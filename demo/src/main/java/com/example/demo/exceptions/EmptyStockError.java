package com.example.demo.exceptions;

public class EmptyStockError extends RuntimeException {

    public EmptyStockError() {
        super("No longer in stock!");
    }
}
