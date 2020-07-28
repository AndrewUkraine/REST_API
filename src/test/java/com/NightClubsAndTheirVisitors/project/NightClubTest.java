package com.NightClubsAndTheirVisitors.project;

import com.NightClubsAndTheirVisitors.project.entities.NightClub;
import com.NightClubsAndTheirVisitors.project.services.NightClubService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@RunWith(SpringRunner.class)
@SpringBootTest
@ExtendWith(SpringExtension.class)
//@ContextHierarchy() boost our tests
//@DataJpaTest //supports rollback after running every test case
class NightClubTest {

    @Autowired
    private NightClubService nightClubService;

    @Test
    void createNightClub() {
        NightClub newNightClub = new NightClub("Ibiza_New");
        NightClub created = nightClubService.saveOrUpdateNightClub(newNightClub);
        newNightClub.setId(created.getId());
        assertEquals(nightClubService.getNightClubById(created.getId()), newNightClub);
    }

    @Test
    void editNightClub() {
        NightClub editedNightClub = new NightClub("Ibiza_New");
        editedNightClub.setNightClubName("Ibiza_Edited");
        editedNightClub.setQuantityOfVisits(100500);
        nightClubService.saveOrUpdateNightClub(editedNightClub);
        assertEquals(nightClubService.getNightClubById(editedNightClub.getId()), editedNightClub);
    }
}

