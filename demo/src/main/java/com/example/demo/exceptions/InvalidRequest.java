package com.example.demo.exceptions;

public class InvalidRequest extends RuntimeException{

    public InvalidRequest() {
        super("Unmatched id provided!");
    }
    public InvalidRequest(int id) {
        super("The Artist is not from the Band with the id: " + id);
    }
}
