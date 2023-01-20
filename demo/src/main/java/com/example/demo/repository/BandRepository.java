package com.example.demo.repository;

import com.example.demo.model.Band;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface BandRepository extends JpaRepository<Band, Integer> {
    Optional<Band> findByBandName (String bandName);

    @Query("select b from Band b where b.yearDebut=?1 or b.yearDisbandment = ?1")
    List<Band> findBandByYear (String year);

    List<Band> findBandByNoMembers(int noMembers);

    @Query("select b from Band b where b.noMembers=?1 and (b.yearDebut =?2 or b.yearDisbandment=?2)")
    List<Band> findByNoMembersAndYear(int noMembers, String year);
}
