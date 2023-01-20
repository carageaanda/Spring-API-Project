package com.example.demo.exceptions;

import com.example.demo.model.Artist;

public class DuplicateArtistStageNameError extends RuntimeException{

    public DuplicateArtistStageNameError(Artist artist) {
        super("Already an Artist with the Stage Name: " + artist.getStageName());
    }
}
