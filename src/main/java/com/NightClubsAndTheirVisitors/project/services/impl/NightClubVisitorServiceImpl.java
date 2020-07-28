package com.NightClubsAndTheirVisitors.project.services.impl;

import com.NightClubsAndTheirVisitors.project.entities.NightClub;
import com.NightClubsAndTheirVisitors.project.entities.NightClubVisitor;
import com.NightClubsAndTheirVisitors.project.repository.NightClubVisitorRepository;
import com.NightClubsAndTheirVisitors.project.repository.NightClubVisitorRepositoryCustom;
import com.NightClubsAndTheirVisitors.project.services.NightClubVisitorService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class NightClubVisitorServiceImpl implements NightClubVisitorService {

    private static final Log logger = LogFactory.getLog(NightClub.class);

    @Autowired
    private NightClubVisitorRepository nightClubVisitorRepository;

    @Autowired
    private NightClubVisitorRepositoryCustom nightClubVisitorRepositoryCustom;

    @Override
    public List<NightClubVisitor> getNightClubVisitorsByNightClubId(Long clubId) {
        return nightClubVisitorRepository.getNightClubVisitorsByNightClubsId(clubId);
    }

    @Override
    public List<NightClubVisitor> getAllNightClubVisitorsWhoNotVisitedNightClubYet(Long clubId) {
        return nightClubVisitorRepositoryCustom.getAllNightClubVisitorsWhoNotVisitedNightClubYet(clubId);
    }

    @Override
    public List<NightClubVisitor> getAllNightClubVisitorsWhoVisitedNightClub(Long clubId) {
        return nightClubVisitorRepositoryCustom.getAllNightClubVisitorsWhoVisitedNightClub(clubId);
    }

    @Override
    public List<NightClubVisitor> getAllActiveVisitors() {
        return nightClubVisitorRepositoryCustom.getAllActiveVisitors();
    }

    @Override
    public List<NightClubVisitor> getAllVisitors() {
        return nightClubVisitorRepositoryCustom.getAllVisitors();
    }

    @Override
    public void deactivationNightClubVisitorById(boolean status, Long id) {
        nightClubVisitorRepositoryCustom.deactivationNightClubVisitorById(status, id);
    }

    @Override
    public void saveOrUpdateNightClubVisitor(NightClubVisitor nightClubVisitor) {
        nightClubVisitorRepositoryCustom.saveAndFlush(nightClubVisitor);
    }

    @Override
    public boolean checkAvoidDoubleEmail(String email, Long id) {
        List<String> nightClubVisitorList = nightClubVisitorRepositoryCustom.checkAvoidDoubleEmailByIdAndEmail(email, id);
        return !nightClubVisitorList.isEmpty();
    }

    @Override
    public boolean checkAvoidDoubleEmailByEmail(String email) {

        List<NightClubVisitor> nightClubVisitorList = nightClubVisitorRepositoryCustom.checkAvoidDoubleEmailByEmail(email);

        if (nightClubVisitorList.isEmpty()) {
            return false;
        }
        else {
           return nightClubVisitorList.stream().anyMatch(v -> v.getEmail().equals(email));
        }
    }

    @Override
    public NightClubVisitor getNightClubVisitorById(Long id) {
        return nightClubVisitorRepository.getNightClubVisitorById(id);
    }
}
