package com.example.demo.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

import static com.example.demo.model.Patterns.DATE_OF_BIRTH;


@Entity
@Table(name = "artists")
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "artist_id")
    private int artistId;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="stage_name")
    private String stageName;


    @Column(name = "birth_date", nullable = false)
    @Pattern(regexp = DATE_OF_BIRTH, message = "Format must be DD-MMM-YYYY and year between 1970-2099")
    private String birthDate;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="band_id")
    private Band band;

    public Artist() {
    }

    public Artist(int artistId, String firstName, String lastName, String stageName, String birthDate) {
        this.artistId = artistId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.stageName = stageName;
        this.birthDate = birthDate;
    }

    public Artist(String firstName, String lastName, String stageName, String birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.stageName = stageName;
        this.birthDate = birthDate;
    }

    public Artist(int artistId, Band band) {
        this.artistId = artistId;
        this.band = band;
    }

    public Artist(int artistId, String firstName, String lastName, String stageName, String birthDate, Band band) {
        this.artistId = artistId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.stageName = stageName;
        this.birthDate = birthDate;
        this.band = band;
    }

    public Artist(String firstName, String lastName, String stageName, String birthDate, Band band) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.stageName = stageName;
        this.birthDate = birthDate;
        this.band = band;
    }

    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public Band getBand() {
        return band;
    }

    public void setBand(Band band) {
        this.band = band;
    }
}
