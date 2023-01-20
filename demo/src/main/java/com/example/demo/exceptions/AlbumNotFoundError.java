package com.example.demo.exceptions;

public class AlbumNotFoundError extends RuntimeException{

    public AlbumNotFoundError() {
        super("Can't find the Album in the Database!");
    }
}
