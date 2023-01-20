package com.example.demo.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="songs")
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="song_id")
    private int songId;

    @Column(nullable = false, name = "song_title")
    private String songTitle;

    @Column(name="song_length")
    private String songLength;

    private Languages language;


    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="album_id")
    private Album album;

    @ManyToMany
    @JoinTable(name="songs_genres", joinColumns = @JoinColumn(name="song_id"), inverseJoinColumns = @JoinColumn(name ="genre_id"))
    private List<Genre> genres = new ArrayList<>();

    public Song() {
    }

    public Song(int songId, String songTitle, String songLength, Languages language) {
        this.songId = songId;
        this.songTitle = songTitle;
        this.songLength = songLength;
        this.language = language;
    }

    public Song(int songId, String songTitle) {
        this.songId = songId;
        this.songTitle = songTitle;
    }

    public Song(String songTitle, String songLength, Languages language) {
        this.songTitle = songTitle;
        this.songLength = songLength;
        this.language = language;
    }

    public Song(int songId, String songTitle, String songLength, Languages language, Album album, List<Genre> genres) {
        this.songId = songId;
        this.songTitle = songTitle;
        this.songLength = songLength;
        this.language = language;
        this.album = album;
        this.genres = genres;
    }

    public Song(String songTitle, String songLength, Languages language, Album album, List<Genre> genres) {
        this.songTitle = songTitle;
        this.songLength = songLength;
        this.language = language;
        this.album = album;
        this.genres = genres;
    }

    public int getSongId() {
        return songId;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    public String getSongLength() {
        return songLength;
    }

    public void setSongLength(String songLength) {
        this.songLength = songLength;
    }

    public Languages getLanguage() {
        return language;
    }

    public void setLanguage(Languages language) {
        this.language = language;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }
}
