package com.example.demo.dto;


import javax.validation.constraints.NotNull;

public class DtoUpdateArtist extends DtoArtistForBand{

    @NotNull(message = "You must provide an id for the Artist!")
    private int id;

    public DtoUpdateArtist() {
    }

    public DtoUpdateArtist(String firstName, String lastName, String stageName, String birthDate, int id) {
        super(firstName, lastName, stageName, birthDate);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
