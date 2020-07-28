package com.NightClubsAndTheirVisitors.project.repository;

import com.NightClubsAndTheirVisitors.project.entities.NightClub;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NightClubRepositoryCustom  extends JpaRepository<NightClub, Long> {

    @Query(value = "SELECT * FROM NIGHT_CLUB WHERE ACTIVE = TRUE ORDER BY QUANTITY_OF_VISITS DESC", nativeQuery = true)
    List<NightClub> getAllActiveNightClubs() throws DataAccessException;

    @Query(value = "SELECT * FROM NIGHT_CLUB", nativeQuery = true)
    List<NightClub> getAllNightClubs() throws DataAccessException;

    //get all Night Clubs which Visitor has visited +++
    @Query(value = "SELECT * FROM NIGHT_CLUB WHERE NIGHT_CLUB_ID IN (SELECT NIGHT_CLUB_ID FROM VISITOR_AND_NIGHT_CLUB WHERE VISITOR_ID = ?)", nativeQuery = true)
    List<NightClub> getAllNightClubsAsAreVisitedByVisitorId (Long visitorId) throws DataAccessException;

    //get all Night Clubs which Visitor has not visited yet +++
    @Query(value = "SELECT * FROM NIGHT_CLUB WHERE NOT NIGHT_CLUB_ID IN (SELECT NIGHT_CLUB_ID FROM VISITOR_AND_NIGHT_CLUB WHERE VISITOR_ID = ?)", nativeQuery = true)
    List<NightClub> getAllNightClubsAsNotAreVisitedByVisitorId (Long visitorId) throws DataAccessException;

    @Query(value = "UPDATE NIGHT_CLUB SET ACTIVE = ? WHERE NIGHT_CLUB_ID = ?", nativeQuery = true)
    void deactivationNightClubById(boolean status, Long id) throws DataAccessException;

   /* @Query(value = "SELECT * FROM night_Club", nativeQuery = true)
    void saveOrUpdateNightClub(NightClub nightClub);*/
}
