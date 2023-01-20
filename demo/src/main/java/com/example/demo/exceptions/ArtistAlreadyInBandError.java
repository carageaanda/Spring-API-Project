package com.example.demo.exceptions;

public class ArtistAlreadyInBandError extends RuntimeException {

    public ArtistAlreadyInBandError() {
        super("The Artist is already in the Band!");
    }
}
