package com.example.demo.dto;



import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.example.demo.model.Patterns.DATE_OF_BIRTH;

public class DtoArtistForBand {


    @NotBlank(message = "You must enter the artist's First Name!")
    @Size(max = 400)
    private String firstName;

    @NotBlank(message = "You must enter the artist's Last Name!")
    @Size(max = 400)
    private String lastName;

    @NotBlank(message = "You must enter the artist's Stage Name!")
    @Size(max = 400)
    private String stageName;

    @NotBlank(message = "You must enter the artist's date of birth!")
    @Pattern(regexp = DATE_OF_BIRTH, message = "The format must be dd-mmm-yyyy and year should be between 1970-2099!")
    private String birthDate;


    public DtoArtistForBand() {
    }

    public DtoArtistForBand(String firstName, String lastName, String stageName, String birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.stageName = stageName;
        this.birthDate = birthDate;
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
}
