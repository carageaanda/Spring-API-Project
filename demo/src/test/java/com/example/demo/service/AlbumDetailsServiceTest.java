package com.example.demo.service;


import com.example.demo.exceptions.AlbumDetailsNotFoundError;
import com.example.demo.model.AlbumDetails;
import com.example.demo.repository.AlbumDetailsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AlbumDetailsServiceTest {


    @InjectMocks
    private AlbumDetailsService albumDetailsService;

    @Mock
    private AlbumDetailsRepository albumDetailsRepository;

    @Test
    public void updateAlbumDetails_shouldUpdateAnExistingAlbum() {
        // given
        int albumId = 1;
        AlbumDetails albumDetails = new AlbumDetails(albumId, 100, 10);
        when(albumDetailsRepository.findById(albumId)).thenReturn(Optional.of(albumDetails));
        when(albumDetailsRepository.save(albumDetails)).thenReturn(albumDetails);
        // when
        AlbumDetails updatedAlbum = albumDetailsService.updateAlbumDetails(albumDetails);

        // then
        assertEquals(albumDetails, updatedAlbum);
        verify(albumDetailsRepository).findById(albumId);
        verify(albumDetailsRepository).save(albumDetails);
    }

    @Test
    public void updateAlbumDetails_shouldThrowAlbumDetailsNotFoundError() {
        // given
        int albumId = 1;
        AlbumDetails albumDetails = new AlbumDetails(albumId, 100, 20);
        when(albumDetailsRepository.findById(albumId)).thenReturn(Optional.empty());

        // when
        assertThrows(AlbumDetailsNotFoundError.class,()-> albumDetailsService.updateAlbumDetails(albumDetails));
    }
}


