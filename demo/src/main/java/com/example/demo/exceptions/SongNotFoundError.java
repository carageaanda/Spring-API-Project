package com.example.demo.exceptions;

public class SongNotFoundError extends RuntimeException{

    public SongNotFoundError() {
        super("Can't find the Song in the Database!");
    }
}
