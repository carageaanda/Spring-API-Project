package com.example.demo.dto;

import com.example.demo.model.AlbumDetails;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

import static com.example.demo.model.Patterns.YEAR_VALIDATION;

public class DtoCreateAlbum {


    @NotBlank(message = "An album must have a name!")
    @Size(max = 300)
    private String albumName;

    @Pattern(regexp = YEAR_VALIDATION, message = "Year must be between 2000 - 2099")
    @Min(2000)
    private String albumYear;

    @Min(value = 0, message = "No of tracks must be positive!")
    private int noTracks;

    @NotBlank(message = "An album must belong to a band!")
    @Size(max = 300)
    private String bandName;

    private AlbumDetails albumDetails;
    private List<DtoSongForAlbum> songs = new ArrayList<>();



    public DtoCreateAlbum() {
    }

    public DtoCreateAlbum(String albumName, String albumYear, int noTracks, String bandName, AlbumDetails albumDetails) {
        this.albumName = albumName;
        this.albumYear = albumYear;
        this.noTracks = noTracks;
        this.bandName = bandName;
        this.albumDetails = albumDetails;
    }

    public DtoCreateAlbum(String albumName, String albumYear, int noTracks, String bandName, AlbumDetails albumDetails, List<DtoSongForAlbum> songs) {
        this.albumName = albumName;
        this.albumYear = albumYear;
        this.noTracks = noTracks;
        this.bandName = bandName;
        this.albumDetails = albumDetails;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getAlbumYear() {
        return albumYear;
    }

    public void setAlbumYear(String year) {
        this.albumYear = albumYear;
    }

    public int getNoTracks() {
        return noTracks;
    }

    public void setNoTracks(int noTracks) {
        this.noTracks = noTracks;
    }

    public String getBandName() {
        return bandName;
    }

    public void setBandName(String bandName) {
        this.bandName = bandName;
    }

    public AlbumDetails getAlbumDetails() {
        return albumDetails;
    }

    public void setAlbumDetails(AlbumDetails albumDetails) {
        this.albumDetails = albumDetails;
    }

    public List<DtoSongForAlbum> getSongs() {
        return songs;
    }

    public void setSongs(List<DtoSongForAlbum> songs) {
        this.songs = songs;
    }
}
