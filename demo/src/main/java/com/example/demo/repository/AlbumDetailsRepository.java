package com.example.demo.repository;

import com.example.demo.model.AlbumDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumDetailsRepository extends JpaRepository<AlbumDetails, Integer> {
}
