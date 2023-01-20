package com.example.demo.dto;


import javax.validation.constraints.*;

import static com.example.demo.model.Patterns.YEAR_VALIDATION;

public class DtoUpdateSimpleAlbum {

    @NotNull(message = "You must provide an id for the Album!")
    private int id;

    @NotBlank(message = "You must enter the Album's Title!")
    @Size(max = 400)
    private String albumTitle;

    @Pattern(regexp = YEAR_VALIDATION, message = "Year must be between 2000 and 2099!")
    @Min(2000)
    private String albumYear;

    @Min(value = 0, message = "Number of Tracks must be a positive number!")
    private int noTracks;

    public DtoUpdateSimpleAlbum() {
    }

    public DtoUpdateSimpleAlbum(int id, String albumTitle, String albumYear, int noTracks) {
        this.id = id;
        this.albumTitle = albumTitle;
        this.albumYear = albumYear;
        this.noTracks = noTracks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }

    public String getAlbumYear() {
        return albumYear;
    }

    public void setAlbumYear(String albumYear) {
        this.albumYear = albumYear;
    }

    public int getNoTracks() {
        return noTracks;
    }

    public void setNoTracks(int noTracks) {
        this.noTracks = noTracks;
    }
}
