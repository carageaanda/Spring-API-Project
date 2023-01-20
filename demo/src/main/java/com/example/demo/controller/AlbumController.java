package com.example.demo.controller;


import com.example.demo.dto.DtoCreateAlbum;
import com.example.demo.dto.DtoSongForAlbum;
import com.example.demo.dto.DtoUpdateSimpleAlbum;
import com.example.demo.exceptions.InvalidRequest;
import com.example.demo.mapper.AlbumMapper;
import com.example.demo.mapper.SongMapper;
import com.example.demo.model.Album;
import com.example.demo.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/album")
@Validated
public class AlbumController {

    private final AlbumService albumService;
    private final AlbumMapper albumMapper;

    @Autowired
    public AlbumController(AlbumService albumService, AlbumMapper albumMapper) {
        this.albumService = albumService;
        this.albumMapper = albumMapper;
    }


    @PostMapping
    public ResponseEntity<?> saveNewAlbum(@Valid @RequestBody DtoCreateAlbum dtoCreateAlbum){
        Album album = albumService.saveNewAlbum(albumMapper.addDtoAlbum(dtoCreateAlbum));
        return ResponseEntity.created(URI.create("/album/" + album.getAlbumId())).body(album);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<?> updateAlbum(@PathVariable int id,
                                    @Valid
                                    @RequestBody DtoUpdateSimpleAlbum dtoUpdateSimpleAlbum) {
        if(id != dtoUpdateSimpleAlbum.getId()) {
            throw new InvalidRequest();
        }
        Album album = albumService.updateAlbum(albumMapper.updateDtoAlbum(dtoUpdateSimpleAlbum));
        return ResponseEntity.ok(album);
    }

    @PutMapping("/{id}/addSong")
    public ResponseEntity<?> updateSongs(@PathVariable int id,
                                         @Valid
                                         @RequestBody DtoSongForAlbum dtoSongForAlbum) {
        SongMapper songMapper = new SongMapper();
        Album album = albumService.addNewSongToAlbum(id, songMapper.createSongDtoToSongFromAlbum(dtoSongForAlbum));
        return ResponseEntity.ok(album);
    }


    @PutMapping("/{id}/putSong/{songId}")
    public ResponseEntity<?> addExistingSongToAlbum(@PathVariable int id, @PathVariable int songId) {
        Album album = albumService.addExistingSongToAlbum(id, songId);
        return ResponseEntity.ok(album);
    }

    @PutMapping("/{id}/deleteSong/{songId}")
    public ResponseEntity<?> deleteSong(@PathVariable int id, @PathVariable int songId) {
        Album album = albumService.deleteSongFromAlbum(id, songId);
        return ResponseEntity.ok(album);
    }

    @GetMapping
    public ResponseEntity<?> get(@RequestParam(required = false) String year,
                                 @RequestParam(required = false) String price) {
        return ResponseEntity.ok().body(albumService.getAllAlbums(year, price));
    }
}
