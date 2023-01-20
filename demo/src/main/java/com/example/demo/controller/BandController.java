package com.example.demo.controller;


import com.example.demo.dto.CreateBandDtoToBand;
import com.example.demo.dto.DtoArtistForBand;
import com.example.demo.dto.UpdateBandDtoToBandArtists;
import com.example.demo.dto.UpdateBandDtoToSimpleBand;
import com.example.demo.exceptions.InvalidRequest;
import com.example.demo.mapper.ArtistMapper;
import com.example.demo.mapper.BandMapper;
import com.example.demo.model.Band;
import com.example.demo.service.BandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/band")
@Validated
public class BandController {

    private final BandService bandService;
    private final BandMapper bandMapper;

    @Autowired
    public BandController(BandService bandService, BandMapper bandMapper) {
        this.bandService = bandService;
        this.bandMapper = bandMapper;
    }

    @PostMapping
    public ResponseEntity<?> saveNewBand(@Valid
                                 @RequestBody CreateBandDtoToBand createBandDtoToBand) {
        Band band = bandService.saveNewBand(bandMapper.addBandDtoToBand(createBandDtoToBand));
        return ResponseEntity.created(URI.create("/band/" + band.getBandId())).body(createBandDtoToBand);
    }

    @PutMapping("/{id}/updateBandDetails")
    public ResponseEntity<?> updateBandDetails(@PathVariable int id,
                                    @Valid
                                    @RequestBody UpdateBandDtoToSimpleBand updateBandDtoToSimpleBand) {
        if(id != updateBandDtoToSimpleBand.getId()) {
            throw new InvalidRequest();
        }
        Band band = bandService.updateBandDetails(bandMapper.updateBandDtoToBand(updateBandDtoToSimpleBand));
        return ResponseEntity.ok(band);
    }

    @PutMapping("/{id}/update/artistsFromBand")
    public ResponseEntity<?> updateArtistFromBand(@PathVariable int id,
                                           @Valid
                                           @RequestBody UpdateBandDtoToBandArtists updateBandDtoToBandArtists) {
        if(id != updateBandDtoToBandArtists.getId()) {
            throw new InvalidRequest();
        }
        Band band = bandService.updateArtistFromBand(bandMapper.updateBandDtoArtists(updateBandDtoToBandArtists));
        return ResponseEntity.ok(updateBandDtoToBandArtists);
    }

    @PutMapping("/{id}/saveNewArtistBand")
    public ResponseEntity<?> saveNewArtistBand(@PathVariable int id,
                                       @Valid
                                       @RequestBody DtoArtistForBand dtoArtistForBand) {
        ArtistMapper artistMapper = new ArtistMapper();
        Band band = bandService.saveNewArtistBand(id, artistMapper.createArtistDtoToArtist(dtoArtistForBand));
        return ResponseEntity.ok(band);
    }

    @PutMapping("/{id}/saveExistingArtistToBand/{artistId}")
    public ResponseEntity<?> saveExistingArtistToBand(@PathVariable int id, @PathVariable int artistId) {
        Band band = bandService.saveExistingArtistToBand(id, artistId);
        return ResponseEntity.ok(band);
    }

    @PutMapping("/{id}/deleteArtistFromBand/{artistId}")
    public ResponseEntity<?> deleteArtistFromBand(@PathVariable int id, @PathVariable int artistId) {
        Band band = bandService.deleteArtistFromBand(id, artistId);
        return ResponseEntity.ok(band);
    }

    @GetMapping
    public ResponseEntity<?> get(@RequestParam(required = false) String noMembers,
                                 @RequestParam(required = false) String year) {
        return ResponseEntity.ok().body(bandService.get(noMembers, year));
    }

}
