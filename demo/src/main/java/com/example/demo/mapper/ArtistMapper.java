package com.example.demo.mapper;


import com.example.demo.dto.DtoArtistForBand;
import com.example.demo.dto.DtoUpdateArtist;
import com.example.demo.model.Artist;
import org.springframework.stereotype.Component;

@Component
public class ArtistMapper {

    public Artist createArtistDtoToArtist(DtoArtistForBand dtoArtistForBand) {
        return new Artist(dtoArtistForBand.getFirstName(), dtoArtistForBand.getLastName(),
                dtoArtistForBand.getStageName(), dtoArtistForBand.getBirthDate());
    }

    public Artist dtoUpdateArtist (DtoUpdateArtist dtoUpdateArtist) {
        return new Artist(dtoUpdateArtist.getId(), dtoUpdateArtist.getFirstName(),
                dtoUpdateArtist.getLastName(), dtoUpdateArtist.getStageName(), dtoUpdateArtist.getBirthDate());
    }
}
