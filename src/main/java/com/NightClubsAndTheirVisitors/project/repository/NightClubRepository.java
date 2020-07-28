package com.NightClubsAndTheirVisitors.project.repository;

import com.NightClubsAndTheirVisitors.project.entities.NightClub;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NightClubRepository extends JpaRepository<NightClub, Long>, NightClubRepositoryCustom {

    NightClub getNightClubById(Long nightClubId) throws DataAccessException;

    @Query(value = "SELECT * FROM NIGHT_CLUB WHERE NIGHT_CLUB_NAME LIKE %?%", nativeQuery = true)
    List<NightClub> getNightClubByNightClubName(String nightClubName) throws DataAccessException;

}
