package com.NightClubsAndTheirVisitors.project.services;

import com.NightClubsAndTheirVisitors.project.entities.NightClub;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NightClubService {

    NightClub getNightClubById(Long nightClubId);

    List<NightClub> getNightClubByNightClubName(String nightClubName);

    List<NightClub> getAllActiveNightClubs();

    List<NightClub> getAllNightClubs();

    List<NightClub> getAllNightClubsAsAreVisitedByVisitorId (Long visitorId);

    List<NightClub> getAllNightClubsAsNotAreVisitedByVisitorId (Long visitorId);

    void deactivationNightClubById(boolean status, Long id);

    NightClub saveOrUpdateNightClub(NightClub nightClub);
}
