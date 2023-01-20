package com.example.demo.service;


import com.example.demo.exceptions.ArtistAlreadyInBandError;
import com.example.demo.exceptions.ArtistNotFoundError;
import com.example.demo.exceptions.BandNotFoundError;
import com.example.demo.exceptions.StageNameError;
import com.example.demo.model.Artist;
import com.example.demo.model.Band;
import com.example.demo.repository.ArtistRepository;
import com.example.demo.repository.BandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArtistService {

    private final ArtistRepository artistRepository;
    private final BandRepository bandRepository;

    @Autowired
    public ArtistService(ArtistRepository artistRepository, BandRepository bandRepository) {
        this.artistRepository = artistRepository;
        this.bandRepository = bandRepository;
    }



    public void checkUniqueStageName(Artist artist) {
        Optional<Artist> foundArtist = artistRepository.findByStageName(artist.getStageName());
        if(foundArtist.isPresent()) {
            throw new StageNameError(artist);
        }
    }

    //Add a New Artist
    public Artist addNewArtist(Artist artist) {
        checkUniqueStageName(artist);
        return artistRepository.save(artist);
    }

    //Update Artist
    public Artist updateArtist(Artist artist) {
        Artist foundArtist = artistRepository.findById(artist.getArtistId()).orElseThrow(
                () -> new ArtistNotFoundError()
        );
        if(!artist.getStageName().equals(foundArtist.getStageName())) {
            checkUniqueStageName(artist);
        }
        // To not ruin the artist when updated in the db
        Artist a = new Artist(artist.getArtistId(), artist.getFirstName(), artist.getLastName(),
                foundArtist.getStageName(), artist.getBirthDate(), foundArtist.getBand());
        return artistRepository.save(a);
    }

    //Add artist to a band
    public Artist addArtistToBand(int id, int bandId) {
        // check band existence
        Optional<Band> foundBand = bandRepository.findById(bandId);
        if(foundBand.isEmpty()) {
            throw new BandNotFoundError();
        }
        // check artist existence
        Optional<Artist> foundArtist = artistRepository.findById(id);
        if(foundArtist.isEmpty()) {
            throw new ArtistNotFoundError();
        }
        // Delete artist from previous band (if exists)
        Artist artist = foundArtist.get();
        if(artist.getBand() != null) {
            Band bandIdd = artist.getBand();
            // artist already in the group
            if(bandIdd.getBandId() == bandId) {
                throw new ArtistAlreadyInBandError();
            }
            List<Artist> artists = bandIdd.getArtists();
            bandIdd.setNoMembers(bandIdd.getNoMembers() - 1);
            artists.remove(foundArtist);
            bandIdd.setArtists(artists);
            artist.setBand(null);
            bandRepository.save(bandIdd);
        }
        Band band = foundBand.get();
        band.setNoMembers(band.getNoMembers() + 1);
        List<Artist> artists = band.getArtists();
        artists.add(artist);
        band.setArtists(artists);
        bandRepository.save(band);
        artist.setBand(band);
        artistRepository.save(artist);
        return artist;
    }

    //Delete an Artist from Artist and Band
    public Artist deleteAnArtist(int id) {
        Artist artist = artistRepository.findById(id).orElseThrow(
                () -> new ArtistNotFoundError()
        );
        Band band = artist.getBand();
        band.setNoMembers(band.getNoMembers() - 1);
        bandRepository.save(band);
        artistRepository.delete(artist);
        return artist;
    }
    //Get all Artists
    public List<Artist> getAllArtists() {
        return artistRepository.findAll();
    }

}
