package com.NightClubsAndTheirVisitors.project.repository;

import com.NightClubsAndTheirVisitors.project.entities.NightClubVisitor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NightClubVisitorRepositoryCustom  extends JpaRepository<NightClubVisitor, Long> {

    //club needs to know who is potential client in the future +++
    @Query(value = "SELECT * FROM NIGHT_CLUB_VISITOR WHERE not VISITOR_ID  IN (SELECT VISITOR_ID  FROM VISITOR_AND_NIGHT_CLUB WHERE NIGHT_CLUB_ID  = ?)", nativeQuery = true)
    List<NightClubVisitor> getAllNightClubVisitorsWhoNotVisitedNightClubYet (Long clubId) throws DataAccessException;

    //club needs to know who are visitors  +++
    @Query(value = "SELECT * FROM NIGHT_CLUB_VISITOR WHERE VISITOR_ID  IN (SELECT VISITOR_ID  FROM VISITOR_AND_NIGHT_CLUB WHERE NIGHT_CLUB_ID  = ?)", nativeQuery = true)
    List<NightClubVisitor> getAllNightClubVisitorsWhoVisitedNightClub (Long clubId) throws DataAccessException;

    //get list active visitors +++
    @Query(value = "SELECT * FROM NIGHT_CLUB_VISITOR WHERE ACTIVE = true", nativeQuery = true)
    List<NightClubVisitor> getAllActiveVisitors() throws DataAccessException;

    //get all visitors +++
    @Query(value = "SELECT * FROM NIGHT_CLUB_VISITOR", nativeQuery = true)
    List<NightClubVisitor> getAllVisitors() throws DataAccessException;

    @Query(value = "UPDATE NIGHT_CLUB SET ACTIVE = ? WHERE NIGHT_CLUB_ID = ?", nativeQuery = true)
    void deactivationNightClubVisitorById(boolean status, Long id) throws DataAccessException;

    /*@Query(value = "SELECT * FROM night_Club_Visitor WHERE active = 'A'", nativeQuery = true)
    void saveOrUpdateNightClubVisitor(NightClubVisitor nightClubVisitor);*/

    @Query(value = "SELECT EMAIL FROM  NIGHT_CLUB_VISITOR WHERE EMAIL = ? AND VISITOR_ID NOT = ?", nativeQuery = true)
    List<String> checkAvoidDoubleEmailByIdAndEmail(String email, Long id) throws DataAccessException;

    @Query(value = "SELECT * FROM  NIGHT_CLUB_VISITOR WHERE EMAIL = ?", nativeQuery = true)
    List<NightClubVisitor> checkAvoidDoubleEmailByEmail(String email) throws DataAccessException;

}
