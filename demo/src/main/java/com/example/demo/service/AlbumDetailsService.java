package com.example.demo.service;


import com.example.demo.exceptions.AlbumDetailsNotFoundError;
import com.example.demo.model.AlbumDetails;
import com.example.demo.repository.AlbumDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlbumDetailsService {

    private final AlbumDetailsRepository albumDetailsRepository;

    @Autowired
    public AlbumDetailsService(AlbumDetailsRepository albumDetailsRepository) {
        this.albumDetailsRepository = albumDetailsRepository;
    }

    //Update table AlbumDetails
    public AlbumDetails updateAlbumDetails(AlbumDetails albumDetails) {
        AlbumDetails albumDetailsFound = albumDetailsRepository.findById(albumDetails.getIdAlbumDetails()).
                orElseThrow(()->{throw new AlbumDetailsNotFoundError();});
        return albumDetailsRepository.save(albumDetails);
    }
}
