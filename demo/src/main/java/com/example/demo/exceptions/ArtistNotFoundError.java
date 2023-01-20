package com.example.demo.exceptions;

public class ArtistNotFoundError extends RuntimeException{

    public ArtistNotFoundError() {
        super("Can't find the artist in the database!");
    }
}
