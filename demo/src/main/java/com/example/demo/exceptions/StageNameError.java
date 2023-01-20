package com.example.demo.exceptions;

import com.example.demo.model.Artist;

public class StageNameError extends RuntimeException{

    public StageNameError(Artist artist) {
        super("Already an Artist with the Stage Name: " + artist.getStageName());
    }

}
