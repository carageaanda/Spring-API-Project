package com.example.demo.exceptions;

public class SongAlreadyInAlbumError extends RuntimeException{

    public SongAlreadyInAlbumError() {
        super("Song is already in an album!");
    }
}
