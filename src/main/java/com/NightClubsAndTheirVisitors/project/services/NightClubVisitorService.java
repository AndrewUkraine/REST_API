package com.NightClubsAndTheirVisitors.project.services;

import com.NightClubsAndTheirVisitors.project.entities.NightClubVisitor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NightClubVisitorService {

    List<NightClubVisitor> getNightClubVisitorsByNightClubId (Long clubId);

    List<NightClubVisitor> getAllNightClubVisitorsWhoNotVisitedNightClubYet (Long clubId);

    List<NightClubVisitor> getAllNightClubVisitorsWhoVisitedNightClub (Long clubId);

    List<NightClubVisitor> getAllActiveVisitors();

    List<NightClubVisitor> getAllVisitors();

    void deactivationNightClubVisitorById(boolean status, Long id);

    void saveOrUpdateNightClubVisitor(NightClubVisitor nightClubVisitor);

    boolean checkAvoidDoubleEmail(String email, Long id);

    boolean checkAvoidDoubleEmailByEmail(String email);

    NightClubVisitor getNightClubVisitorById (Long id);

}
