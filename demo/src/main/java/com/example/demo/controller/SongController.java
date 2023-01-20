package com.example.demo.controller;

import com.example.demo.dto.DtoSongForAlbum;
import com.example.demo.mapper.SongMapper;
import com.example.demo.model.Genre;
import com.example.demo.model.Song;
import com.example.demo.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/song")
@Valid
public class SongController {

    private final SongService songService;
    private final SongMapper songMapper;

    @Autowired
    public SongController(SongService songService, SongMapper songMapper) {
        this.songService = songService;
        this.songMapper = songMapper;
    }

    @PostMapping
    public ResponseEntity<?> addNewSong(@Valid
                                 @RequestBody DtoSongForAlbum dtoSongForAlbum) {
        Song song = songService.addNewSong(songMapper.createSongDtoToSongFromAlbum(dtoSongForAlbum));
        return ResponseEntity.created(URI.create("/song/" + song.getSongId())).body(song);
    }

    @PutMapping("/{id}/setSongInAlbum/{albumId}")
    public ResponseEntity<?> setSongInAlbum(@PathVariable int id, @PathVariable int albumId) {
        Song song = songService.setSongInAlbum(id, albumId);
        return ResponseEntity.ok(song);
    }

    @PutMapping("/{id}/setNewGenre")
    public ResponseEntity<?> setNewGenresToSong(@PathVariable int id,
                                          @Valid
                                          @RequestBody List<Genre> genres) {
        Song song = songService.setNewGenresToSong(id, genres);
        return ResponseEntity.ok(song);
    }

    @PutMapping("/{id}/setExistingGenre/{genreId}")
    public ResponseEntity<?> setExistingGenreToSong(@PathVariable int id, @PathVariable int genreId) {
        Song song = songService.setExistingGenreToSong(id, genreId);
        return ResponseEntity.ok(song);
    }

    @DeleteMapping("/delete/genre")
    public ResponseEntity<?> deleteSongsWithGenre(@RequestParam String type) {
        List<Song> songs = songService.deleteSongsWithGenre(type);
        return ResponseEntity.ok(songs);
    }

    @GetMapping
    public List<Song> getSongsByLanguage(@RequestParam String language) {
        return songService.getSongsByLanguage(language);
    }
}
