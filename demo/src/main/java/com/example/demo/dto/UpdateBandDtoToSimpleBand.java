package com.example.demo.dto;
import javax.validation.constraints.*;

import static com.example.demo.model.Patterns.YEAR_VALIDATION;

public class UpdateBandDtoToSimpleBand {

    @NotNull(message = "You must provide an id for the Band!")
    private int id;

    @NotBlank(message = "You must enter the Band's Name")
    @Size(max = 400)
    private String bandName;

    @NotNull(message = "You must enter the year of debut!")
    @Pattern(regexp = YEAR_VALIDATION, message = "Enter a valid year! Year must be between 2000 and 2099!")
    @Min(2000)
    private String yearDebut;

    @Pattern(regexp = YEAR_VALIDATION, message = "Enter a valid year! Year must be between 2000 and 2099 or it can be null!")
    private String yearDisbandment;

    public UpdateBandDtoToSimpleBand() {
    }

    public UpdateBandDtoToSimpleBand(int id, String bandName, String yearDebut, String yearDisbandment) {
        this.id = id;
        this.bandName = bandName;
        this.yearDebut = yearDebut;
        this.yearDisbandment = yearDisbandment;
    }

    public UpdateBandDtoToSimpleBand(String bandName, String yearDebut, String yearDisbandment) {
        this.bandName = bandName;
        this.yearDebut = yearDebut;
        this.yearDisbandment = yearDisbandment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBandName() {
        return bandName;
    }

    public void setBandName(String bandName) {
        this.bandName = bandName;
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
}
