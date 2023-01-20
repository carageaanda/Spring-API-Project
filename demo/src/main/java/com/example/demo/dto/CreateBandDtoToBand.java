package com.example.demo.dto;


import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.demo.model.Patterns.YEAR_VALIDATION;

public class CreateBandDtoToBand {



    @NotBlank(message = "You must enter the band's name!")
    @Size(max = 400)
    private String bandName;

    private int noMembers;

    @NotNull(message = "You must enter the year of debut!")
    @Pattern(regexp = YEAR_VALIDATION, message = "Enter a valid year! Year must be between 2000 and 2099")
    @Min(2000)
    private String yearDebut;

    @Pattern(regexp = YEAR_VALIDATION, message = "Enter a valid year! Year must be between 2000 and 2099 or it can be null")
    private String yearDisbandment;

    private List<DtoArtistForBand> artists = new ArrayList<>();

    public CreateBandDtoToBand() {
    }

    public CreateBandDtoToBand(String bandName, int noMembers, String yearDebut, String yearDisbandment) {
        this.bandName = bandName;
        this.noMembers = noMembers;
        this.yearDebut = yearDebut;
        this.yearDisbandment = yearDisbandment;
    }

    public CreateBandDtoToBand(String bandName, int noMembers, String yearDebut, String yearDisbandment, List<DtoArtistForBand> artists) {
        this.bandName = bandName;
        this.noMembers = noMembers;
        this.yearDebut = yearDebut;
        this.yearDisbandment = yearDisbandment;
        this.artists = artists;
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

    public List<DtoArtistForBand> getArtists() {
        return artists;
    }

    public void setArtists(List<DtoArtistForBand> artists) {
        this.artists = artists;
    }
}
