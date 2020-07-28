package com.NightClubsAndTheirVisitors.project;

import com.NightClubsAndTheirVisitors.project.entities.NightClub;
import com.NightClubsAndTheirVisitors.project.entities.NightClubVisitor;
import com.NightClubsAndTheirVisitors.project.entities.enums.RoleType;
import com.NightClubsAndTheirVisitors.project.repository.NightClubRepository;
import com.NightClubsAndTheirVisitors.project.repository.NightClubVisitorRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.*;


@SpringBootApplication
public class RubyPlayTaskApplication {

    private static final Log log = LogFactory.getLog(RubyPlayTaskApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(RubyPlayTaskApplication.class, args);
    }

    @Autowired
    NightClubRepository nightClubRepository;

    @Autowired
    NightClubVisitorRepository nightClubVisitorRepository;

    @Bean
    public CommandLineRunner demo() {
        return (args) -> {

            /*------------------NightClubs-------------------------------*/

            NightClub nightClub = new NightClub("Club_number_1");
            NightClub nightClub_2 = new NightClub("Club_number_2");
            NightClub nightClub_3 = new NightClub("Club_number_3");
            List<NightClub> nightClubList = Arrays.asList(nightClub, nightClub_2, nightClub_3);
            nightClubRepository.saveAll(nightClubList);

            log.debug("Test NightClub with ID: " + nightClub.getId() + " created");

            /*-------------------Visitors----------------------------------*/

            NightClubVisitor nightClubVisitor = new NightClubVisitor("Visitor_1", "11111","visitor1@gmail.com", Collections.singleton(RoleType.USER));
            NightClubVisitor nightClubVisitor_2 = new NightClubVisitor("Visitor_2", "22222", "visitor2@gmail.com", Collections.singleton(RoleType.USER));
            NightClubVisitor nightClubVisitor_3 = new NightClubVisitor("Visitor_3", "33333", "visitor3@gmail.com", Collections.singleton(RoleType.SUPER_USER));
            List<NightClubVisitor> nightClubVisitorList = Arrays.asList(nightClubVisitor, nightClubVisitor_2, nightClubVisitor_3);

            //INJECT CLUB TO VISITOR
            Set<NightClub> listNightClubs = new HashSet<>();
            listNightClubs.add(nightClub_2);
            listNightClubs.add(nightClub_3);
           // nightClubVisitor_2.setNightClubs(listNightClubs);
          //  nightClubVisitor_3.setNightClubs(listNightClubs);
            nightClubVisitorRepository.saveAll(nightClubVisitorList);

            log.debug("Night ClubVisitor with ID: " + nightClubVisitor.getId() + " created");
            log.debug("Night ClubVisitor with ID: " + nightClubVisitor.getId() + " created");


        };
    }
}

