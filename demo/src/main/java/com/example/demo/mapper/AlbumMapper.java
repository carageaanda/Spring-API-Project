package com.example.demo.mapper;


import com.example.demo.dto.DtoCreateAlbum;
import com.example.demo.dto.DtoUpdateSimpleAlbum;
import com.example.demo.exceptions.BandNotFoundError;
import com.example.demo.model.Album;
import com.example.demo.model.Band;
import com.example.demo.repository.BandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AlbumMapper {


    private final BandRepository bandRepository;

    @Autowired
    public AlbumMapper(BandRepository bandRepository) {
        this.bandRepository = bandRepository;
    }
    public Album addDtoAlbum(DtoCreateAlbum dtoCreateAlbum) {
        // verify if group exists, otherwise we cannot add the album
        if(bandRepository.findByBandName(dtoCreateAlbum.getBandName()).isEmpty()) {
            throw new BandNotFoundError();
        }

        Band band = bandRepository.findByBandName(dtoCreateAlbum.getBandName()).get();
        if(dtoCreateAlbum.getSongs() != null) {
            return new Album(dtoCreateAlbum.getAlbumName(), dtoCreateAlbum.getAlbumYear(),
                    dtoCreateAlbum.getNoTracks(), band, dtoCreateAlbum.getAlbumDetails(),
                    dtoCreateAlbum.getSongs());
        }
        return new Album(dtoCreateAlbum.getAlbumName(), dtoCreateAlbum.getAlbumYear(),
                dtoCreateAlbum.getNoTracks(), band, dtoCreateAlbum.getAlbumDetails());
    }


    public Album updateDtoAlbum(DtoUpdateSimpleAlbum dtoUpdateSimpleAlbum) {
        return new Album(dtoUpdateSimpleAlbum.getId(), dtoUpdateSimpleAlbum.getAlbumTitle(), dtoUpdateSimpleAlbum.getAlbumYear(), dtoUpdateSimpleAlbum.getNoTracks());
    }
}
