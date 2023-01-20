package com.example.demo.controller;


import com.example.demo.exceptions.InvalidRequest;
import com.example.demo.model.AlbumDetails;
import com.example.demo.service.AlbumDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/albumDetails")
@Validated
public class AlbumDetailsController {

    private final AlbumDetailsService albumDetailsService;

    @Autowired
    public AlbumDetailsController(AlbumDetailsService albumDetailsService) {
        this.albumDetailsService = albumDetailsService;
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<?> update(@PathVariable int id,
                                    @Valid
                                    @RequestBody AlbumDetails albumDetails) {
        System.out.println(id);
        System.out.println(albumDetails.getIdAlbumDetails());
        if(id != albumDetails.getIdAlbumDetails()) {
            throw new InvalidRequest();
        }
        return ResponseEntity.ok().body(albumDetailsService.updateAlbumDetails(albumDetails));
    }


}
