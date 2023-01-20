package com.example.demo.service;


import com.example.demo.exceptions.*;
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

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BandServiceTest {

    @InjectMocks
    private BandService bandService;

    @Mock
    private BandRepository bandRepository;

    @Mock
    private ArtistRepository artistRepository;

    @Mock
    private BandNotFoundError bandNotFoundError;

    @Test
    @DisplayName("Band created successfully")
    void add() {
        Band band = new Band("TestBandName", 4, "2020", "2025");
        when(bandRepository.findByBandName(band.getBandName())).thenReturn(Optional.empty());
        Band savedBand = new Band(1, "TestBandName", 4, "2020", "2025");
        when(bandRepository.save(band)).thenReturn(savedBand);

        Band result = bandService.saveNewBand(band);

        assertNotNull(result);
        assertEquals(savedBand.getBandName(), result.getBandName());
        assertEquals(savedBand.getNoMembers(), result.getNoMembers());
        assertEquals(savedBand.getYearDebut(), result.getYearDebut());
        assertEquals(savedBand.getYearDisbandment(), result.getYearDisbandment());

        verify(bandRepository).findByBandName(band.getBandName());
        verify(bandRepository).save(band);
    }

    @Test
    @DisplayName("Group NOT created - Duplicate Name")
    void addThrowsError() {
        Band band = new Band("TestBandName", 4, "2020", "2025");
        when(bandRepository.findByBandName(band.getBandName())).thenReturn(Optional.of(band));

        DuplicateBandError error = assertThrows(DuplicateBandError.class,
                () -> bandService.saveNewBand(band));

        assertNotNull(error);
    }


    @Test
    void testSaveNewArtistBand_ShouldReturnUpdatedBand() {
        int id = 1;
        Band band = new Band();
        band.setBandId(id);
        band.setNoMembers(5);
        Artist artist = new Artist();
        artist.setStageName("NewArtistName");
        when(bandRepository.findById(id)).thenReturn(Optional.of(band));
        when(artistRepository.findByStageName(artist.getStageName())).thenReturn(Optional.empty());
        when(artistRepository.save(artist)).thenReturn(artist);
        when(bandRepository.save(band)).thenReturn(band);
        Band returnedBand = bandService.saveNewArtistBand(id, artist);
        assertEquals(band, returnedBand);
        assertEquals(band.getArtists().get(band.getArtists().size() - 1), artist);
        assertEquals(6, band.getNoMembers());
    }



    @Test
    public void testUpdateBandDetails_bandNotFound() {
        // given
        Band band = new Band();
        band.setBandId(1);
        when(bandRepository.findById(1)).thenReturn(Optional.empty());

        // when
        assertThrows(BandNotFoundError.class,()-> bandService.updateBandDetails(band));

        // then
        verify(bandRepository).findById(1);
    }

    @Test
    void testGet_noMembersAndYear_success() {
        // given
        Band band1 = new Band();
        Band band2 = new Band();
        when(bandRepository.findByNoMembersAndYear(5, "2022")).thenReturn(Arrays.asList(band1, band2));

        // when
        List<Band> bands = (List<Band>) bandService.get("5", "2022");

        // then
        assertEquals(Arrays.asList(band1, band2), bands);
        verify(bandRepository, times(1)).findByNoMembersAndYear(5, "2022");
    }


    @Test
    void testGet_noMembers_success() {
        // given
        Band band1 = new Band();
        Band band2 = new Band();
        when(bandRepository.findBandByNoMembers(5)).thenReturn(Arrays.asList(band1, band2));

        // when
        List<Band> bands = (List<Band>) bandService.get("5", null);

        // then
        assertEquals(Arrays.asList(band1, band2), bands);
        verify(bandRepository, times(1)).findBandByNoMembers(5);
    }



    @Test
    void testGet_year_success() {
        // given
        Band band1 = new Band();
        Band band2 = new Band();
        when(bandRepository.findBandByYear("2022")).thenReturn(Arrays.asList(band1, band2));

        // when
        List<Band> bands = (List<Band>) bandService.get(null, "2022");

        // then
        assertEquals(Arrays.asList(band1, band2), bands);
        verify(bandRepository, times(1)).findBandByYear("2022");
    }

    @Test
    void testGet_all_success() {
        // given
        Band band1 = new Band();
        Band band2 = new Band();
        List<Band> expectedBands = Arrays.asList(band1, band2);
        when(bandRepository.findAll()).thenReturn(expectedBands);

        // when
        Object result = bandService.get(null, null);

        // then
        assertEquals(expectedBands, result);
    }

    @Test
    @DisplayName("Delete artist from band - success")
    void deleteArtistFromBandSuccess() {
        // arrange
        int bandId = 1;
        int artistId = 2;
        Band band = new Band(bandId, "The Beatles", "09-Oct-1999", null);
        Artist artist = new Artist(artistId, "John", "Lennon", "John", "09-Oct-1999", band);
        when(bandRepository.findById(bandId)).thenReturn(Optional.of(band));
        when(artistRepository.findById(artistId)).thenReturn(Optional.of(artist));
        when(artistRepository.save(artist)).thenReturn(artist);
        when(bandRepository.save(any())).thenAnswer((Answer<Band>) invocation -> {
            Object[] args = invocation.getArguments();
            return (Band) args[0];
        });

        // act
        Band updatedBand = bandService.deleteArtistFromBand(bandId, artistId);

        // assert
        assertNotNull(updatedBand);
        assertEquals(bandId, updatedBand.getBandId());
        assertEquals(band.getBandName(), updatedBand.getBandName());
        assertEquals(band.getYearDebut(), updatedBand.getYearDebut());
        assertEquals(band.getYearDisbandment(), updatedBand.getYearDisbandment());
        assertEquals(band.getNoMembers(), updatedBand.getNoMembers());
        assertFalse(updatedBand.getArtists().contains(artist));
        verify(bandRepository).findById(bandId);
        verify(artistRepository).findById(artistId);
        verify(artistRepository).save(artist);
        verify(bandRepository).save(updatedBand);
    }



    // saveExistingArtistToBand

    @Test
    public void testSaveExistingArtistToBand_bandNotFound() {
        when(bandRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(BandNotFoundError.class, () -> bandService.saveExistingArtistToBand(1, 1));
    }


    @Test
    public void testSaveExistingArtistToBand_artistNotFound() {
        when(bandRepository.findById(1)).thenReturn(Optional.of(new Band()));
        when(artistRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(ArtistNotFoundError.class, () -> bandService.saveExistingArtistToBand(1, 1));
    }

    @Test
    public void testSaveExistingArtistToBand_artistAlreadyInBand() {
        Band band = new Band();
        band.setBandId(1);
        Artist artist = new Artist();
        artist.setBand(band);
        when(bandRepository.findById(1)).thenReturn(Optional.of(band));
        when(artistRepository.findById(1)).thenReturn(Optional.of(artist));
        assertThrows(ArtistAlreadyInBandError.class, () -> bandService.saveExistingArtistToBand(1, 1));
    }

    @Test
    public void testSaveExistingArtistToBand_success() {
        Band band = new Band();
        band.setBandId(1);
        band.setNoMembers(1);
        Artist artist = new Artist();
        Artist artist2 = new Artist();
        artist2.setBand(band);
        when(bandRepository.findById(1)).thenReturn(Optional.of(band));
        when(artistRepository.findById(1)).thenReturn(Optional.of(artist));
        Band savedBand = bandService.saveExistingArtistToBand(1, 1);
        assertEquals(2, savedBand.getNoMembers());
        verify(bandRepository, times(1)).save(band);
        verify(artistRepository).save(artist);
    }

    @Test
    public void testUpdateArtistFromBand_bandNotFound() {
        Band band = new Band();
        band.setBandId(1);
        when(bandRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(BandNotFoundError.class, () -> bandService.updateArtistFromBand(band));
    }

    @Test
    public void testUpdateArtistFromBand_noMembersNotEqual() {
        Band band = new Band();
        band.setBandId(1);
        band.setNoMembers(1);
        band.setArtists(new ArrayList<>());
        when(bandRepository.findById(1)).thenReturn(Optional.of(band));
        assertThrows(NoMembersNotEqualError.class, () -> bandService.updateArtistFromBand(band));
    }


}
