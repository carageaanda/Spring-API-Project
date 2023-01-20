package com.example.demo.exceptions;

public class DuplicateGenreError extends RuntimeException{
    public DuplicateGenreError() {
        super("Duplicate Genre Error!");
    }
}
