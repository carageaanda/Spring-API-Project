package com.example.demo.service;


import com.example.demo.exceptions.*;
import com.example.demo.model.Artist;
import com.example.demo.model.Band;
import com.example.demo.repository.ArtistRepository;
import com.example.demo.repository.BandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BandService {

    private final BandRepository bandRepository;
    private final ArtistRepository artistRepository;

    @Autowired
    public BandService(BandRepository bandRepository, ArtistRepository artistRepository) {
        this.bandRepository = bandRepository;
        this.artistRepository = artistRepository;
    }

    public void checkUniqueName(Band band) {
        Optional<Band> foundBand = bandRepository.findByBandName(band.getBandName());
        if (foundBand.isPresent()) {
            throw new DuplicateBandError(band);
        }
    }

    public void checkUniqueStageName(Band band) {
        for (Artist artist: band.getArtists()) {
            if(!artistRepository.findByStageName(artist.getStageName()).isEmpty()){
                throw new DuplicateArtistStageNameError(artist);
            }
        }
    }

    // Save a new Band
    public Band saveNewBand(Band band) {
        checkUniqueName(band);
        if(!band.getArtists().isEmpty()) {
            if(band.getNoMembers() != band.getArtists().size()) {
                throw new NoMembersNotEqualError(band);
            }
            checkUniqueStageName(band);
        }
        bandRepository.save(band);

        // Set the artists of the band
        for (Artist artist: band.getArtists()) {
            Artist a = artistRepository.getOne(artist.getArtistId());
            a.setBand(band);
            artistRepository.save(a);
        }
        return band;
    }

    //Update Band details
    public Band updateBandDetails(Band band) {
        Band foundBand = bandRepository.findById(band.getBandId()).orElseThrow(
                () -> new BandNotFoundError(band)
        );
        if(!band.getBandName().equals(foundBand.getBandName())) {
            checkUniqueName(band);
        }
        // To not ruin the no of members
        Band b = new Band(band.getBandId(), band.getBandName(),
                foundBand.getNoMembers(), band.getYearDebut(),
                band.getYearDisbandment());
        return bandRepository.save(b);
    }

    //Update Artist and Number of members

    public Band updateArtistFromBand(Band band) {
        Band foundBand = bandRepository.findById(band.getBandId()).orElseThrow(
                () -> new BandNotFoundError(band)
        );
        if(band.getNoMembers() != band.getArtists().size()) {
            throw new NoMembersNotEqualError(band);
        }
        checkUniqueStageName(band);
        // To maintain the data from the found band
        Band b = new Band(band.getBandId(), foundBand.getBandName(),
                band.getNoMembers(), foundBand.getYearDebut(),
                foundBand.getYearDisbandment(), band.getArtists());
        bandRepository.save(b);

        // Set the artists of the band and delete the previous ones
        for (Artist artist: foundBand.getArtists()) {
            artist.setBand(null);
            artistRepository.save(artist);
        }
        for (Artist artist: b.getArtists()) {
            Optional<Artist> a = artistRepository.findByStageName(artist.getStageName());
            if(a.isPresent()) a.get().setBand(b);
            artistRepository.save(a.get());
        }
        return b;
    }

    //Add a new artist to a band
    public Band saveNewArtistBand(int id, Artist artist) {
        Optional<Band> foundBand = bandRepository.findById(id);
        if(foundBand.isEmpty()) {
            throw new BandNotFoundError();
        }
        if(artistRepository.findByStageName(artist.getStageName()).isPresent()) {
            throw new DuplicateArtistStageNameError(artist);
        }
        Band band = foundBand.get();

        // Set the band of artists
        artist.setBand(band);
        artistRepository.save(artist);

        // Set band -> +1 for noMembers and added artist to the list
        band.setNoMembers(band.getNoMembers() + 1);
        List<Artist> artists = band.getArtists();
        artists.add(artist);
        band.setArtists(artists);
        return bandRepository.save(band);
    }

    // Add an existing artist to a band
    public Band saveExistingArtistToBand(int id, int artistId) {
        // check band existence
        Optional<Band> foundBand = bandRepository.findById(id);
        if(foundBand.isEmpty()) {
            throw new BandNotFoundError();
        }
        // check artist existence
        Optional<Artist> foundArtist = artistRepository.findById(artistId);
        if(foundArtist.isEmpty()) {
            throw new ArtistNotFoundError();
        }
        // delete artist from previous group (if exists)
        Artist artist = foundArtist.get();
        if(artist.getBand() != null) {
            Band previousBand = artist.getBand();
            // artist already in the group
            if(previousBand.getBandId() == id) {
                throw new ArtistAlreadyInBandError();
            }
            List<Artist> artists = previousBand.getArtists();
            previousBand.setNoMembers(previousBand.getNoMembers() - 1);
            artists.remove(artist);
            previousBand.setArtists(artists);
            artist.setBand(null);
            bandRepository.save(previousBand);
        }
        // put artist in Band
        Band band = foundBand.get();
        band.setNoMembers(band.getNoMembers() + 1);
        List<Artist> artistList = band.getArtists();
        artistList.add(artist);
        band.setArtists(artistList);
        bandRepository.save(band);
        artist.setBand(band);
        artistRepository.save(artist);
        return band;
    }

    // Delete an Artist from a Band
    public Band deleteArtistFromBand(int id, int artistId) {

        // Check Band existence
        Optional<Band> foundBand = bandRepository.findById(id);
        if(foundBand.isEmpty()) {
            throw new BandNotFoundError();
        }
        // Check Artist existence
        Optional<Artist> foundArtist = artistRepository.findById(artistId);
        if(foundArtist.isEmpty()) {
            throw new ArtistNotFoundError();
        }
        // Artist is not from the Band provided
        if(foundArtist.get().getBand() == null
                || foundArtist.get().getBand().getBandId() != id) {
            throw new InvalidRequest(id);
        }
        Artist artist = foundArtist.get();

        // Artist is not in the Band any-longer
        artist.setBand(null);
        artistRepository.save(artist);

        // delete artist from group
        Band band = foundBand.get();
        band.setNoMembers(band.getNoMembers() - 1);
        List<Artist> artists = band.getArtists();
        artists.remove(artist);
        band.setArtists(artists);
        return bandRepository.save(band);
    }

    // Get All Bands
    public Object get(String  noMembers, String year) {
        if(noMembers != null) {
            if(year != null) {
                return bandRepository.findByNoMembersAndYear(Integer.valueOf(noMembers), year);
            }
            return bandRepository.findBandByNoMembers(Integer.valueOf(noMembers));
        }
        if(year != null) {
            return bandRepository.findBandByYear(year);
        }
        return bandRepository.findAll();
    }


}