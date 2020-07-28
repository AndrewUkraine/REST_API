package com.NightClubsAndTheirVisitors.project.services.impl;

import com.NightClubsAndTheirVisitors.project.entities.NightClub;
import com.NightClubsAndTheirVisitors.project.repository.NightClubRepository;
import com.NightClubsAndTheirVisitors.project.repository.NightClubRepositoryCustom;
import com.NightClubsAndTheirVisitors.project.services.NightClubService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class NightClubServiceImpl  implements NightClubService {

    private static final Log logger = LogFactory.getLog(NightClub.class);

    @Autowired
    private NightClubRepository nightClubRepository;

    @Autowired
    private NightClubRepositoryCustom nightClubRepositoryCustom;

    @Override
    public NightClub getNightClubById(Long nightClubId) {
        return nightClubRepository.getNightClubById(nightClubId);
    }

    @Override
    public List<NightClub> getNightClubByNightClubName(String nightClubName) {
        return nightClubRepository.getNightClubByNightClubName(nightClubName);
    }

    @Override
    public List<NightClub> getAllActiveNightClubs() {
        return nightClubRepositoryCustom.getAllActiveNightClubs();
    }

    @Override
    public List<NightClub> getAllNightClubs() {
        return nightClubRepositoryCustom.getAllNightClubs();
    }

    @Override
    public List<NightClub> getAllNightClubsAsAreVisitedByVisitorId(Long visitorId) {
        return nightClubRepositoryCustom.getAllNightClubsAsAreVisitedByVisitorId(visitorId);
    }

    @Override
    public List<NightClub> getAllNightClubsAsNotAreVisitedByVisitorId(Long visitorId) {
        return nightClubRepositoryCustom.getAllNightClubsAsNotAreVisitedByVisitorId(visitorId);
    }

    @Override
    public void deactivationNightClubById(boolean status, Long id) {
        nightClubRepositoryCustom.deactivationNightClubById(status, id);
    }

    @Override
    public NightClub saveOrUpdateNightClub(NightClub nightClub) {
        nightClubRepositoryCustom.saveAndFlush(nightClub);
        return nightClub;
    }

    public boolean compareNamesOfNightClubs(NightClub nightClub, List<NightClub> nightClubList) {

        if (checkToEqualsId(nightClub, nightClubList) && checkForEqualsClubName(nightClub)){
            return false;
        }

       return nightClubList.stream().anyMatch(nC -> nC.getNightClubName().contains(nightClub.getNightClubName()));
    }

    public boolean checkForEqualsClubName(NightClub nightClub) {
        return nightClub.getNightClubName().equalsIgnoreCase(getNightClubById(nightClub.getId()).getNightClubName());
    }

    public boolean checkToEqualsId(NightClub nightClub, List<NightClub> nightClubList) {
        return nightClubList.stream().anyMatch(nC -> nC.getId().equals(nightClub.getId()));
    }
}
