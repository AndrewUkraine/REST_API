package com.NightClubsAndTheirVisitors.project.controllers;

import com.NightClubsAndTheirVisitors.project.entities.NightClub;
import com.NightClubsAndTheirVisitors.project.entities.NightClubVisitor;
import com.NightClubsAndTheirVisitors.project.exceptions.BadRequestException;
import com.NightClubsAndTheirVisitors.project.exceptions.NotFoundException;
import com.NightClubsAndTheirVisitors.project.services.impl.NightClubServiceImpl;
import com.NightClubsAndTheirVisitors.project.services.impl.NightClubVisitorServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@RestController
@RequestMapping("/night-club")
@ApiOperation(value = "Night Club Controller", response = Iterable.class)
public class NightClubController {

    private static final Log logger = LogFactory.getLog(NightClubController.class);

    @Autowired
    NightClubServiceImpl nightClubService;

    @Autowired
    NightClubVisitorServiceImpl nightClubVisitorService;

    @GetMapping(value = "/all")
    @ApiOperation(value = "View a list of available Night Clubs", response = Iterable.class)
    public List<NightClub> getAllNightClubs() throws NotFoundException {

        List<NightClub> nightClubList = nightClubService.getAllNightClubs();

        if (nightClubList.isEmpty()) {
            String message = "No one Night Club is found";
            logger.error(message);
            throw new NotFoundException(message);
        }

        return nightClubList;
    }

    @GetMapping(value = "/all-active")
    @ApiOperation(value = "View a list of available active Night Clubs", response = Iterable.class)
    public List<NightClub> getAllActiveNightClubs() throws NotFoundException {

        List<NightClub> activeNightClubList = nightClubService.getAllActiveNightClubs();

        if (activeNightClubList.isEmpty()) {
            String message = "No one Active Night Club is found";
            logger.error(message);
            throw new NotFoundException(message);
        }

        return activeNightClubList;
    }

    // TODO: re-implement via RequestParam
    @PostMapping
    @ApiOperation(value = "Create a new Night Club", response = NightClub.class)
    public void createNightClub(@RequestBody NightClub nightClub, HttpServletResponse response) throws IOException {

        checkToEmptyFieldName(nightClub);

        try {
            nightClub.setQuantityOfVisits(0);
            nightClubService.saveOrUpdateNightClub(nightClub);
        } catch (Exception e) {
            String message = nightClub.getId() == null ? "Can't create Night Club" + e.getMessage() : "Can't update Night Club with id: " + nightClub.getId() + " " + e.getMessage();
            logger.error(message);
            e.printStackTrace();
        }

        logger.trace("Was created Night Club: " + nightClub);

        response.sendRedirect("/night-club/all");
    }

    // TODO: re-implement via RequestParam. For now it is not working.
    @PatchMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Update a Night Club", response = Iterable.class)
    public RedirectView editNightClub(@RequestBody NightClub nightClub, HttpServletResponse response) throws IOException {

        checkToEmptyFieldName(nightClub);

        try {
            nightClubService.saveOrUpdateNightClub(nightClub);
        } catch (Exception e) {
            String message = nightClub.getId() == null ? "Can't create Night Club" + e.getMessage() : "Can't update Night Club with id: " + nightClub.getId() + " " + e.getMessage();
            logger.error(message);
            e.printStackTrace();
        }

        logger.trace("Was updated Night Club: " + nightClub);

        return new RedirectView("/night-club/" + nightClub.getId());
    }

    private void checkToEmptyFieldName(@RequestBody NightClub nightClub) {
        if (StringUtils.isBlank(nightClub.getNightClubName())) {
            String message = nightClub.getId() == null ? "Night Club Name is required !" : "Night Club Name is required ! : " + nightClub.getId();
            logger.error(message);
            throw new BadRequestException(message);
        }

        List<NightClub> nightClubList = nightClubService.getAllNightClubs();

        if (nightClubService.compareNamesOfNightClubs(nightClub, nightClubList)) {
            String message = "Night Club with name: " + nightClub.getNightClubName() + " exists. Please, set another name";
            logger.error(message);
            throw new BadRequestException(message);
        }
    }

    @GetMapping(value = "/visited/{id}")
    @ApiOperation(value = "View a list of Night Clubs are visited", response = Iterable.class)
    public List<NightClub> getAllNightClubsAsAreVisitedByVisitorId(@PathVariable Long id) throws NotFoundException {

        if (StringUtils.isBlank(id.toString())) {
            String message = "Please, specify ID";
            logger.error(message);
            throw new BadRequestException(message);
        }

        List<NightClub> nightClubList = nightClubService.getAllNightClubsAsAreVisitedByVisitorId(id);

        if (nightClubList.isEmpty()) {
            String message = "No one Night Club is visited";
            logger.error(message);
            throw new NotFoundException(message);
        }

        return nightClubList;
    }

    @GetMapping(value = "/not-visited/{id}")
    @ApiOperation(value = "View a list of Night Clubs are not visited", response = Iterable.class)
    public List<NightClub> getAllNightClubsAsNotAreVisitedByVisitorId(@PathVariable Long id) throws NotFoundException {

        if (StringUtils.isBlank(id.toString())) {
            String message = "Please, specify ID";
            logger.error(message);
            throw new BadRequestException(message);
        }

        List<NightClub> nightClubList = nightClubService.getAllNightClubsAsNotAreVisitedByVisitorId(id);

        if (nightClubList.isEmpty()) {
            String message = "No one Night Club for visit is found";
            logger.error(message);
            throw new NotFoundException(message);
        }

        return nightClubList;
    }

    @GetMapping(value = "club/{id}")
    @ApiOperation(value = "View Night Club by Id", response = Iterable.class)
    public NightClub getNightClubById(@PathVariable Long id) throws NotFoundException {

        if (StringUtils.isBlank(id.toString())) {
            String message = "Please, specify ID";
            logger.error(message);
            throw new BadRequestException(message);
        }

        NightClub nightClub = nightClubService.getNightClubById(id);

        if (nightClub == null) {
            String message = "Night Club with Id: " + id + " not found";
            logger.error(message);
            throw new NotFoundException(message);
        }

        logger.debug("Night Club with Id: " + nightClub.getId() + " found");
        return nightClub;
    }

    @GetMapping(value = "search/{name}")
    @ApiOperation(value = "Search Night Club by name", response = Iterable.class)
    public List<NightClub> getNightClubByNightClubName(@PathVariable String name) {

        if (StringUtils.isBlank(name)) {
            String message = "Please, specify search parameter";
            logger.error(message);
            throw new BadRequestException(message);
        }

        List<NightClub> nightClubList = nightClubService.getNightClubByNightClubName(name);

        if (nightClubList.isEmpty()) {
            String message = "No one Night Club with name: " + name.toUpperCase() + " is found";
            logger.error(message);
            throw new NotFoundException(message);
        }

        logger.trace("For search Night Club with name: " + name.toUpperCase() + " was found " + nightClubList.size() + " Night Clubs");

        return nightClubList;
    }

    @GetMapping(value = "/club/visit")
    @ApiOperation(value = "Visit Night Club by VisitorId and  NightClubId", response = Iterable.class)
    public RedirectView onVisit(@RequestParam Long nightClubVisitorId, @RequestParam Long nightClubId) {

        if (StringUtils.isBlank(nightClubVisitorId.toString()) || StringUtils.isBlank(nightClubId.toString())) {
            String message = "Please, specify ID";
            logger.error(message);
            throw new BadRequestException(message);
        }

        NightClub nightClub = nightClubService.getNightClubById(nightClubId);
        NightClubVisitor nightClubVisitor = nightClubVisitorService.getNightClubVisitorById(nightClubVisitorId);

        if (nightClub == null) {
            String message = "Night Club with " + nightClubId + " not exists";
            logger.error(message);
            throw new NotFoundException(message);
        }
        if (nightClubVisitor == null) {
            String message = "Night Visitor with " + nightClubId + " not exists";
            logger.error(message);
            throw new NotFoundException(message);
        }

        nightClub.setNightClubVisitors(Stream.of(nightClubVisitor).collect(Collectors.toSet()));
        nightClubVisitor.setNightClubs(Stream.of(nightClub).collect(Collectors.toSet()));

        int QUANTITY_OF_VISITS_NIGHT_CLUB = nightClub.getQuantityOfVisits() + 1;
        nightClub.setQuantityOfVisits(QUANTITY_OF_VISITS_NIGHT_CLUB);

        nightClubService.saveOrUpdateNightClub(nightClub);
        nightClubVisitorService.saveOrUpdateNightClubVisitor(nightClubVisitor);

        logger.debug("Night Club Visitor with Id: " + nightClubVisitor.getId() + " visited Night Club with ID: " + nightClub.getId());

        return new RedirectView("/night-club/all");
    }
}
