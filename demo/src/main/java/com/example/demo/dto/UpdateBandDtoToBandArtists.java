package com.example.demo.dto;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class UpdateBandDtoToBandArtists {


    @NotNull(message = "You must enter an id for the Band!")
    private int id;

    @NotNull(message = "A band must have at least one member!")
    @Min(1)
    private int noMembers;

    private List<DtoArtistForBand> artists = new ArrayList<>();

    public UpdateBandDtoToBandArtists() {
    }

    public UpdateBandDtoToBandArtists(int id, int noMembers, List<DtoArtistForBand> artists) {
        this.id = id;
        this.noMembers = noMembers;
        this.artists = artists;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNoMembers() {
        return noMembers;
    }

    public void setNoMembers(int noMembers) {
        this.noMembers = noMembers;
    }

    public List<DtoArtistForBand> getArtists() {
        return artists;
    }

    public void setArtists(List<DtoArtistForBand> artists) {
        this.artists = artists;
    }
}
