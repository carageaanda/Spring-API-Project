package com.example.demo.exceptions;

import com.example.demo.model.Album;

public class DuplicateAlbumError extends RuntimeException{

    public DuplicateAlbumError(Album album) {
        super("Already an Album with the title \"" + album.getAlbumName() +
                "\" in the Band " + album.getBand().getBandName());
    }

    public DuplicateAlbumError(String title, Album foundAlbum) {
        super("Already an Album with the title \"" + title +
                "\" in the Band " + foundAlbum.getBand().getBandName());
    }
}
