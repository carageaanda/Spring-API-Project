package com.example.demo.exceptions;

import com.example.demo.model.Album;

public class NoTracksNotEqualError extends RuntimeException{

    public NoTracksNotEqualError(Album album) {
        super("No of tracks provided (" + album.getNoTrakcs() +
                ") is not equal to the number of songs from the list attached (" +
                album.getSongs().size() + ")!");
    }
}
