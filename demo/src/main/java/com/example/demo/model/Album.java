package com.example.demo.model;


import com.example.demo.dto.DtoSongForAlbum;
import com.example.demo.mapper.SongMapper;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="albums")
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "album_id")
    private int albumId;

    @Column(name = "album_name")
    private String albumName;

    @Column(name = "album_year")
    private String albumYear;

    @Column(name = "no_tracks")
    private int noTrakcs;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "band_id")
    private Band band;

    @OneToOne
    @JoinColumn(name = "album_details_id")
    private AlbumDetails albumDetails;

    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL)
    private List<Song> songs = new ArrayList<>();

    @ManyToMany(mappedBy = "albumList")
    @JsonIgnore
    private List<Shop> shops = new ArrayList<>();


    public Album() {
    }

    public Album(int albumId, String albumName, String albumYear, int noTracks, Band band, AlbumDetails albumDetails, List<Song> songs) {
        this.albumId = albumId;
        this.albumName = albumName;
        this.albumYear = albumYear;
        this.noTrakcs = noTracks;
        this.band = band;
        this.albumDetails = albumDetails;
        this.songs = songs;
    }

    public Album(int albumId, String albumName, String albumYear, int noTracks) {
        this.albumId = albumId;
        this.albumName = albumName;
        this.albumYear = albumYear;
        this.noTrakcs = noTracks;
    }


    public Album(String albumName, String albumYear, int noTracks) {
        this.albumName = albumName;
        this.albumYear = albumYear;
        this.noTrakcs = noTracks;
    }


    public Album(String albumName, String albumYear, int noTracks, Band band, AlbumDetails albumDetails, List<DtoSongForAlbum> songs) {
        this.albumName = albumName;
        this.albumYear = albumYear;
        this.noTrakcs = noTracks;
        this.band = band;
        this.albumDetails = albumDetails;
        SongMapper songMapper = new SongMapper();
        List<Song> songs1 = new ArrayList<>();
        for(DtoSongForAlbum s: songs) {
            songs1.add(songMapper.createSongDtoToSongFromAlbum(s));
        }
        this.songs = songs1;
    }

    public Album(String albumName, String albumYear, int noTracks, Band band, AlbumDetails albumDetails) {
        this.albumName = albumName;
        this.albumYear = albumYear;
        this.noTrakcs = noTracks;
        this.band = band;
        this.albumDetails = albumDetails;
    }

    public Album(String albumName, Band band) {
        this.albumName = albumName;
        this.band = band;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
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

    public void setAlbumYear(String albumYear) {
        this.albumYear = albumYear;
    }

    public int getNoTrakcs() {
        return noTrakcs;
    }

    public void setNoTracks(int noTracks) {
        this.noTrakcs = noTracks;
    }

    public Band getBand() {
        return band;
    }

    public void setBand(Band band) {
        this.band = band;
    }

    public AlbumDetails getAlbumDetails() {
        return albumDetails;
    }

    public void setAlbumDetails(AlbumDetails albumDetails) {
        this.albumDetails = albumDetails;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }
}