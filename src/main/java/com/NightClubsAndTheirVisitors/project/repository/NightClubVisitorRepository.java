package com.NightClubsAndTheirVisitors.project.repository;

import com.NightClubsAndTheirVisitors.project.entities.NightClubVisitor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NightClubVisitorRepository extends JpaRepository<NightClubVisitor, Long>, NightClubVisitorRepositoryCustom {

    //club needs to know who has visited a night Club
    List<NightClubVisitor> getNightClubVisitorsByNightClubsId(Long clubId) throws DataAccessException;

    NightClubVisitor getNightClubVisitorById (Long userId) throws DataAccessException;

}
