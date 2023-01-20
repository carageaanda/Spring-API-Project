package com.example.demo.controller;


import com.example.demo.dto.DtoArtistForBand;
import com.example.demo.dto.DtoUpdateArtist;
import com.example.demo.exceptions.InvalidRequest;
import com.example.demo.mapper.ArtistMapper;
import com.example.demo.model.Artist;
import com.example.demo.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/artist")
@Validated
public class ArtistController {

    private final ArtistService artistService;
    private final ArtistMapper artistMapper;

    @Autowired
    public ArtistController(ArtistService artistService, ArtistMapper artistMapper) {
        this.artistService = artistService;
        this.artistMapper = artistMapper;
    }

    @PostMapping
    public ResponseEntity<?> add(@Valid
                                 @RequestBody DtoArtistForBand request) {
        Artist artist = artistService.addNewArtist(artistMapper.createArtistDtoToArtist(request));
        return ResponseEntity.created(URI.create("/artist/" + artist.getArtistId())).body(artist);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<?> update(@PathVariable int id,
                                    @Valid
                                    @RequestBody DtoUpdateArtist dtoUpdateArtist) {
        if(id != dtoUpdateArtist.getId()) {
            throw new InvalidRequest();
        }
        Artist artist = artistService.updateArtist(artistMapper.dtoUpdateArtist(dtoUpdateArtist));
        return ResponseEntity.ok(artist);
    }

    @PutMapping("/{id}/putBand/{bandId}")
    public ResponseEntity<?> addArtistToBand(@PathVariable int id, @PathVariable int bandId) {
        Artist artist = artistService.addArtistToBand(id, bandId);
        return ResponseEntity.ok(artist);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteAnArtist(@PathVariable int id) {
        Artist artist = artistService.deleteAnArtist(id);
        return ResponseEntity.ok(artist);
    }

    @GetMapping
    public ResponseEntity<?> getAllArtists() {
        return ResponseEntity.ok().body(artistService.getAllArtists());
    }

}
