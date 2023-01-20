package com.example.demo.service;


import com.example.demo.exceptions.ArtistNotFoundError;
import com.example.demo.exceptions.DuplicateArtistStageNameError;
import com.example.demo.exceptions.StageNameError;
import com.example.demo.model.Artist;
import com.example.demo.model.Band;
import com.example.demo.repository.ArtistRepository;
import com.example.demo.repository.BandRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
public class ArtistServiceTest {

    @InjectMocks
    private ArtistService artistService;

    @Mock
    private  ArtistRepository artistRepository;

    @Mock
    private  BandRepository bandRepository;


    @Test
    @DisplayName("The artist was created successfully!")
    void saveNewArtistTest(){
        Artist artist = new Artist("firstNameTest", "lastNameTest", "stageNameTest", "03-May-2001");
        when(artistRepository.findByStageName(artist.getStageName())).thenReturn(Optional.empty());
        Artist savedArtist = new Artist(1, "firstNameTest", "lastNameTest", "stageNameTest", "03-May-2001");
        when(artistRepository.save(artist)).thenReturn(savedArtist);

        Artist result = artistService.addNewArtist(artist);

        assertNotNull(result);
        assertEquals(savedArtist.getArtistId(), result.getArtistId());
        assertEquals(savedArtist.getFirstName(), result.getFirstName());
        assertEquals(savedArtist.getLastName(), result.getLastName());
        assertEquals(savedArtist.getStageName(), result.getStageName());
        assertEquals(savedArtist.getBirthDate(), result.getBirthDate());

        verify(artistRepository).findByStageName(artist.getStageName());
        verify(artistRepository).save(artist);
    }



    @Test
    void testCheckUniqueStageName() {
        // Set up the test
        Artist artist = new Artist();
        artist.setStageName("TestArtist");
        when(artistRepository.findByStageName("TestArtist")).thenReturn(Optional.empty());

        // Test that the method does not throw an exception when the stage name is unique
        artistService.checkUniqueStageName(artist);

        // Test that the method throws a StageNameError when the stage name is not unique
        when(artistRepository.findByStageName("TestArtist")).thenReturn(Optional.of(new Artist()));
        assertThrows(StageNameError.class, () -> artistService.checkUniqueStageName(artist));
    }


    @Test
    void testUpdateArtistThrowsError() {
        when(artistRepository.findById(1)).thenReturn(Optional.empty());
        Artist artist = new Artist("firstName", "lastName", "stageName", "03-May-2001");
        artist.setArtistId(1);
        assertThrows(ArtistNotFoundError.class, () -> artistService.updateArtist(artist));
    }


    @Test
    @DisplayName("Delete artist - success")
    void deleteArtistSuccess() {
        // arrange
        int id = 1;
        Artist artist = new Artist(id, "John", "Lennon", "John", "09-Oct-1999", null);
        Band band = new Band(2, "BandTest", "09-May-2000", "09-Dec-2010");
        artist.setBand(band);
        when(artistRepository.findById(id)).thenReturn(Optional.of(artist));
        doNothing().when(artistRepository).delete(artist);
        when(bandRepository.save(any())).thenAnswer((Answer<Band>) invocation -> {
            Object[] args = invocation.getArguments();
            return (Band) args[0];
        });

        // act
        Artist deletedArtist = artistService.deleteAnArtist(id);

        // assert
        assertNotNull(deletedArtist);
        assertEquals(artist.getArtistId(), deletedArtist.getArtistId());
        assertEquals(artist.getFirstName(), deletedArtist.getFirstName());
        assertEquals(artist.getLastName(), deletedArtist.getLastName());
        assertEquals(artist.getStageName(), deletedArtist.getStageName());
        assertNotNull(deletedArtist.getBand());
        assertEquals(band.getBandId(), deletedArtist.getBand().getBandId());
        assertEquals(band.getBandName(), deletedArtist.getBand().getBandName());
        assertEquals(band.getNoMembers() , deletedArtist.getBand().getNoMembers());
        verify(artistRepository).findById(id);
        verify(artistRepository).delete(artist);
        verify(bandRepository).save(band);
    }


    @Test
    @DisplayName("Get all artists")
    void get() {
        Artist artist = new Artist(1, "firstNameTest", "lastNameTest", "stageNameTest", "03-May-2001");
        when(artistRepository.findAll()).thenReturn(List.of(artist));

        List<Artist> result = artistService.getAllArtists();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(artist, result.get(0));

        verify(artistRepository, never()).findByStageName((any()));
        verify(artistRepository).findAll();
    }


}
