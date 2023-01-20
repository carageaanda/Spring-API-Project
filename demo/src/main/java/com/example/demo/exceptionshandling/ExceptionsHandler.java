package com.example.demo.exceptionshandling;


import com.example.demo.exceptions.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler({
        AlbumDetailsNotFoundError.class, BandNotFoundError.class, DuplicateAlbumError.class, NoTracksNotEqualError.class, InvalidRequest.class,
            ArtistNotFoundError.class, EmptyStockError.class, StageNameError.class, ArtistAlreadyInBandError.class, DuplicateBandError.class,
    DuplicateArtistStageNameError.class, NoMembersNotEqualError.class, SongAlreadyInAlbumError.class, DuplicateGenreError.class, NoSongsWithThisGenreError.class})

    public ResponseEntity handle(Exception e) {
    return ResponseEntity.badRequest().body(e.getMessage());
    }
}
