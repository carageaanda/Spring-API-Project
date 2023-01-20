package com.example.demo.service;


import com.example.demo.exceptions.*;
import com.example.demo.model.Album;
import com.example.demo.model.AlbumDetails;
import com.example.demo.model.Band;
import com.example.demo.model.Song;
import com.example.demo.repository.AlbumDetailsRepository;
import com.example.demo.repository.AlbumRepository;
import com.example.demo.repository.SongRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AlbumServiceTest {


    @InjectMocks
    private AlbumService albumService;

    @Mock
    private AlbumRepository albumRepository;
    @Mock
    private AlbumDetailsRepository albumDetailsRepository;

    @Mock
    private SongRepository songRepository;

    @BeforeEach
    public void setUp() {
        albumService = new AlbumService(albumRepository, albumDetailsRepository, songRepository);
    }




    @Test
    void testAddNewSongToAlbum_ShouldThrowAlbumNotFoundError() {
        int id = 1;
        Song song = new Song();
        when(albumRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(AlbumNotFoundError.class, () -> albumService.addNewSongToAlbum(id,song));
    }



    @Test
    void testUpdateAlbum_ShouldThrowAlbumNotFoundError() {
        int id = 1;
        Album album = new Album();
        album.setAlbumId(id);
        when(albumRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(AlbumNotFoundError.class, () -> albumService.updateAlbum(album));
    }



    //GET ALL
    @Test
    public void getAllAlbums_shouldRetrieveAllAlbums() {
        // given
        Album album1 = new Album(1, "albumTest1", "2011", 10);
        Album album2 = new Album(2, "albumTest2", "2010", 20);
        List<Album> expectedAlbums = Arrays.asList(album1, album2);
        when(albumRepository.findAll()).thenReturn(expectedAlbums);

        // when
        List<Album> retrievedAlbums = (List<Album>) albumService.getAllAlbums(null, null);

        // then
        assertEquals(expectedAlbums, retrievedAlbums);
        verify(albumRepository).findAll();
    }



    @Test
    public void getAllAlbums_shouldRetrieveAlbumsByYear() {
        // given
        String year = "2010";
        Album album1 = new Album(1, "albumTest1", "2011", 10);
        Album album2 = new Album(2, "albumTest2", "2010", 20);
        List<Album> expectedAlbums = Arrays.asList(album1, album2);
        when(albumRepository.findByAlbumYear(year)).thenReturn(expectedAlbums);
        // when
        List<Album> retrievedAlbums = (List<Album>) albumService.getAllAlbums(year, null);

        // then
        assertEquals(expectedAlbums, retrievedAlbums);
        verify(albumRepository).findByAlbumYear(year);
    }


    @Test
    public void testAddExistingSongToAlbum_success() {
        int id = 1;
        int songId = 2;

        Album album = new Album();
        album.setAlbumId(id);
        album.setNoTracks(3);
        List<Song> songList = new ArrayList<>();
        album.setSongs(songList);
        Optional<Album> albumOpt = Optional.of(album);

        Song song = new Song();
        song.setSongId(songId);
        Optional<Song> songOpt = Optional.of(song);

        when(albumRepository.findById(anyInt())).thenReturn(albumOpt);
        when(songRepository.findById(anyInt())).thenReturn(songOpt);
        albumService.addExistingSongToAlbum(id, songId);
        verify(albumRepository, times(1)).save(album);
        verify(songRepository, times(1)).save(song);
    }

    @Test
    public void testDeleteSongFromAlbum_AlbumNotFound() {
        when(albumRepository.findById(eq(1))).thenReturn(Optional.empty());

        AlbumNotFoundError exception = assertThrows(AlbumNotFoundError.class,
                () -> albumService.deleteSongFromAlbum(1, 1));

        assertEquals("Can't find the Album in the Database!", exception.getMessage());
    }

    @Test
    public void testDeleteSongFromAlbum_SongNotFound() {
        when(albumRepository.findById(eq(1))).thenReturn(Optional.of(new Album()));
        when(songRepository.findById(eq(1))).thenReturn(Optional.empty());

        SongNotFoundError exception = assertThrows(SongNotFoundError.class,
                () -> albumService.deleteSongFromAlbum(1, 1));

        assertEquals("Can't find the Song in the Database!", exception.getMessage());
    }

    @Test
    public void testDeleteSongFromAlbum_InvalidRequest() {
        Album album = new Album();
        int albumId = 1;
        album.setAlbumId(albumId);
        when(albumRepository.findById(eq(albumId))).thenReturn(Optional.of(album));

        Song song = new Song();
        song.setAlbum(new Album());
        when(songRepository.findById(eq(albumId))).thenReturn(Optional.of(song));

        InvalidRequest exception = assertThrows(InvalidRequest.class,
                () -> albumService.deleteSongFromAlbum(1, 1));

        assertEquals("The Artist is not from the Band with the id: " + albumId, exception.getMessage());
    }


    @Test
    public void testBuyAlbum_whenAlbumNotFound_thenThrowError() {
        // Arrange
        when(albumRepository.findById(anyInt())).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(AlbumNotFoundError.class, () -> albumService.buyAlbum(1, "1", "FR"));
    }

    @Test
    public void testBuyAlbum_whenStockEmpty_thenThrowError() {
        // Arrange
        Album album = new Album();
        AlbumDetails details = new AlbumDetails();
        details.setQuantity(0);
        album.setAlbumDetails(details);
        when(albumRepository.findById(anyInt())).thenReturn(Optional.of(album));

        // Act and Assert
        assertThrows(EmptyStockError.class, () -> albumService.buyAlbum(1, "1", "FR"));
    }

    @Test
    public void testBuyAlbum_whenNotEnoughMoney_thenThrowError() {
        // Arrange
        Album album = new Album();
        AlbumDetails details = new AlbumDetails();
        details.setQuantity(2);
        details.setPrice(20);
        album.setAlbumDetails(details);
        when(albumRepository.findById(anyInt())).thenReturn(Optional.of(album));

        // Act and Assert
        assertThrows(NoMoneyError.class, () -> albumService.buyAlbum(1, "2", "FR"));
    }


    /** @Test
    public void testDeleteSongFromAlbum_Success() {
    Album album = new Album();
    album.setAlbumId(1);
    album.setNoTracks(2);
    album.setSongs(new ArrayList<>(Arrays.asList(new Song())));
    when(albumRepository.findById(eq(1))).thenReturn(Optional.of(album));

    Song song = new Song();
    song.setAlbum(album);
    when(songRepository.findById(eq(1))).thenReturn(Optional.of(song));
    Album updatedAlbum = albumService.deleteSongFromAlbum(1, 1);
    assertEquals(1, updatedAlbum.getNoTrakcs());
    assertEquals(1, updatedAlbum.getSongs().size());
    verify(songRepository).save(eq(song));
    verify(albumRepository).save(eq(updatedAlbum));
    }*/



}

