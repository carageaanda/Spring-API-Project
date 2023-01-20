package com.example.demo.model;


import com.example.demo.dto.DtoArtistForBand;
import com.example.demo.mapper.ArtistMapper;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="band")
public class Band {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "band_id")
    private int bandId;

    @Column(name="band_name")
    private String bandName;

    @Column(name="no_members")
    private int noMembers;

    @Column(name="year_debut")
    private String yearDebut;

    @Column(name="year_disbandment")
    private String yearDisbandment;

    @OneToMany(mappedBy = "band", cascade = CascadeType.ALL)
    private List<Artist> artists = new ArrayList<>();

    @OneToMany(mappedBy = "band")
    private List<Album> albums = new ArrayList<>();

    public Band() {
    }


    public Band(int bandId, String bandName, int noMembers, String yearDebut, String yearDisbandment) {
        this.bandId = bandId;
        this.bandName = bandName;
        this.noMembers = noMembers;
        this.yearDebut = yearDebut;
        this.yearDisbandment = yearDisbandment;
    }

    public Band(String bandName, int noMembers, String yearDebut, String yearDisbandment) {
        this.bandName = bandName;
        this.noMembers = noMembers;
        this.yearDebut = yearDebut;
        this.yearDisbandment = yearDisbandment;
    }

    public Band(String bandName, String yearDebut, String yearDisbandment) {
        this.bandName = bandName;
        this.yearDebut = yearDebut;
        this.yearDisbandment = yearDisbandment;
    }

    public Band(int bandId, String bandName, String yearDebut, String yearDisbandment) {
        this.bandId = bandId;
        this.bandName = bandName;
        this.yearDebut = yearDebut;
        this.yearDisbandment = yearDisbandment;
    }


    public Band(int bandId, String bandName, int noMembers, String yearDebut, String yearDisbandment, List<Artist> artists) {
        this.bandId = bandId;
        this.bandName = bandName;
        this.noMembers = noMembers;
        this.yearDebut = yearDebut;
        this.yearDisbandment = yearDisbandment;
        this.artists = artists;
    }

    public Band(int bandId, String bandName, int noMembers, String yearDebut, String yearDisbandment, List<Artist> artists, List<Album> albums) {
        this.bandId = bandId;
        this.bandName = bandName;
        this.noMembers = noMembers;
        this.yearDebut = yearDebut;
        this.yearDisbandment = yearDisbandment;
        this.artists = artists;
        this.albums = albums;
    }

    //facuta
    public Band(String bandName, int noMembers, String yearDebut, String yearDisbandment, List<DtoArtistForBand> artists) {
        this.bandName = bandName;
        this.noMembers = noMembers;
        this.yearDebut = yearDebut;
        this.yearDisbandment = yearDisbandment;
        ArtistMapper artistMapper = new ArtistMapper();
        List<Artist> artists1 = new ArrayList<>();
        for (DtoArtistForBand a: artists) {
            artists1.add(artistMapper.createArtistDtoToArtist(a));
        }
        this.artists = artists1;
    }

    public Band(int id, int noMembers, List<DtoArtistForBand> artists) {
        this.bandId = id;
        this.noMembers = noMembers;
        ArtistMapper artistMapper = new ArtistMapper();
        List<Artist> artists1 = new ArrayList<>();
        for (DtoArtistForBand a: artists) {
            artists1.add(artistMapper.createArtistDtoToArtist(a));
        }
        this.artists = artists1;
    }

    public int getBandId() {
        return bandId;
    }

    public void setBandId(int bandId) {
        this.bandId = bandId;
    }

    public String getBandName() {
        return bandName;
    }

    public void setBandName(String bandName) {
        this.bandName = bandName;
    }

    public int getNoMembers() {
        return noMembers;
    }

    public void setNoMembers(int noMembers) {
        this.noMembers = noMembers;
    }

    public String getYearDebut() {
        return yearDebut;
    }

    public void setYearDebut(String yearDebut) {
        this.yearDebut = yearDebut;
    }

    public String getYearDisbandment() {
        return yearDisbandment;
    }

    public void setYearDisbandment(String yearDisbandment) {
        this.yearDisbandment = yearDisbandment;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }
}
