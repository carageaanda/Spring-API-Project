package com.example.demo.repository;

import com.example.demo.model.Album;
import com.example.demo.model.Band;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface AlbumRepository extends JpaRepository<Album, Integer> {

    Optional<Album> findByAlbumNameAndBand(String albumName, Band band);
    List<Album> findByAlbumYear(String albumYear);

    List<Album> findByAlbumDetails_Price(double price);
}
