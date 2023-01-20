package com.example.demo.mapper;

import com.example.demo.dto.CreateBandDtoToBand;
import com.example.demo.dto.UpdateBandDtoToBandArtists;
import com.example.demo.dto.UpdateBandDtoToSimpleBand;
import com.example.demo.model.Band;
import org.springframework.stereotype.Component;

@Component
public class BandMapper {

    public Band addBandDtoToBand (CreateBandDtoToBand createBandDtoToBand) {
        if(createBandDtoToBand.getArtists() != null) {
            return new Band(createBandDtoToBand.getBandName(), createBandDtoToBand.getNoMembers(),
                    createBandDtoToBand.getYearDebut(), createBandDtoToBand.getYearDisbandment(), createBandDtoToBand.getArtists());
        }
        return new Band(createBandDtoToBand.getBandName(), createBandDtoToBand.getNoMembers(),
                createBandDtoToBand.getYearDebut(), createBandDtoToBand.getYearDisbandment());
    }

    public Band updateBandDtoToBand(UpdateBandDtoToSimpleBand updateBandDtoToSimpleBand) {
        return new Band(updateBandDtoToSimpleBand.getId(), updateBandDtoToSimpleBand.getBandName(), updateBandDtoToSimpleBand.getYearDebut(), updateBandDtoToSimpleBand.getYearDisbandment());
    }

    public Band updateBandDtoArtists(UpdateBandDtoToBandArtists updateBandDtoToBandArtists) {
        return new Band(updateBandDtoToBandArtists.getId(), updateBandDtoToBandArtists.getNoMembers(), updateBandDtoToBandArtists.getArtists());
    }
}
