package com.example.demo.service;


import com.example.demo.exceptions.*;
import com.example.demo.model.Album;
import com.example.demo.model.Genre;
import com.example.demo.model.Languages;
import com.example.demo.model.Song;
import com.example.demo.repository.AlbumRepository;
import com.example.demo.repository.GenreRepository;
import com.example.demo.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class SongService {

    private final SongRepository songRepository;
    private final AlbumRepository albumRepository;
    private final GenreRepository genreRepository;

    @Autowired
    public SongService(SongRepository songRepository, AlbumRepository albumRepository, GenreRepository genreRepository) {
        this.songRepository = songRepository;
        this.albumRepository = albumRepository;
        this.genreRepository = genreRepository;
    }

    //Save a New Song
    public Song addNewSong(Song song) {
        return songRepository.save(song);
    }

    //Set Song in the Album
    public Song setSongInAlbum(int id, int albumId) {
        // check song existence
        Optional<Song> foundSong = songRepository.findById(id);
        if(foundSong.isEmpty()) {
            throw new SongNotFoundError();
        }
        // check album existence
        Optional<Album> foundAlbum = albumRepository.findById(albumId);
        if(foundAlbum.isEmpty()) {
            throw new AlbumNotFoundError();
        }
        Song song = foundSong.get();
        // check if song is already in an album
        if(song.getAlbum() != null) {
            throw new SongAlreadyInAlbumError();
        }
        // put song in album
        Album album = foundAlbum.get();
        album.setNoTracks(album.getNoTrakcs() + 1);
        List<Song> songList = album.getSongs();
        songList.add(song);
        album.setSongs(songList);
        albumRepository.save(album);
        song.setAlbum(album);
        songRepository.save(song);
        return song;
    }

    //Set new genres for Song
    public Song setNewGenresToSong(int id, List<Genre> genres) {
        //check song existence
        Optional<Song> foundSong = songRepository.findById(id);
        if(foundSong.isEmpty()) {
            throw new SongNotFoundError();
        }
        Song song = foundSong.get();
        for (Genre genre: genres) {
            if(genreRepository.findByType(genre.getType()).isPresent()) {
                throw new DuplicateGenreError();
            }
            genre.setSongs(Arrays.asList(song));
            genreRepository.save(genre);
        }
        List<Genre> genreList = song.getGenres();
        for (Genre g: genres) {
            genreList.add(g);
        }
        song.setGenres(genreList);
        return songRepository.save(song);
    }

    //Set existing genre to Song
    public Song setExistingGenreToSong(int id, int genreId) {
        //check song existence
        Optional<Song> foundSong = songRepository.findById(id);
        if(foundSong.isEmpty()) {
            throw new SongNotFoundError();
        }
        //check genre existence
        Optional<Genre> foundGenre = genreRepository.findById(genreId);
        if(foundGenre.isEmpty()) {
            throw new BandNotFoundError();
        }
        Song song = foundSong.get();
        List<Genre> genreList = song.getGenres();
        if(genreList.contains(foundGenre.get())) {
            throw new DuplicateGenreError();
        }
        genreList.add(foundGenre.get());
        song.setGenres(genreList);
        return songRepository.save(song);
    }

    // Delete specific Genre from all Songs with that Genre
    public List<Song> deleteSongsWithGenre(String type) {
        //check genre existence
        Optional<Genre> foundGenre = genreRepository.findByType(type);
        if(foundGenre.isEmpty()) {
            throw new BandNotFoundError();
        }
        //if genre has no songs assigned
        Genre genre = foundGenre.get();
        if(genre.getSongs().isEmpty()) {
            throw new NoSongsWithThisGenreError(type);
        }
        List<Song> songs = genre.getSongs();
        for (Song s : songs) {
            List<Genre> genreList = s.getGenres();
            genreList.remove(genre);
            s.setGenres(genreList);
            songRepository.save(s);
        }
        genre.setSongs(Arrays.asList());
        genreRepository.save(genre);
        return songs;
    }

    // List All Songs in a Certain Language
    public List<Song> getSongsByLanguage(String language) {
        Languages l = Languages.valueOf(language);
        return songRepository.findByLanguage(l);
    }
}
