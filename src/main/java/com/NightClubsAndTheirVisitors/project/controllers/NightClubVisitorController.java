package com.NightClubsAndTheirVisitors.project.controllers;


import com.NightClubsAndTheirVisitors.project.configuration.CustomWebSecurityConfigurerAdapter;
import com.NightClubsAndTheirVisitors.project.entities.NightClubVisitor;
import com.NightClubsAndTheirVisitors.project.entities.enums.RoleType;
import com.NightClubsAndTheirVisitors.project.exceptions.BadRequestException;
import com.NightClubsAndTheirVisitors.project.exceptions.NotFoundException;
import com.NightClubsAndTheirVisitors.project.repository.NightClubVisitorRepository;
import com.NightClubsAndTheirVisitors.project.services.impl.NightClubVisitorServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@RestController
@ApiOperation(value = "Night Club Visitor Controller", response = Iterable.class)
@RequestMapping("/visitor")
public class NightClubVisitorController {

    private static final Log logger = LogFactory.getLog(NightClubVisitorController.class);

    @Autowired
    NightClubVisitorServiceImpl nightClubVisitorService;

    @Autowired
    NightClubVisitorRepository nightClubVisitorRepository;

    @Autowired
    private CustomWebSecurityConfigurerAdapter customWebSecurityConfigurerAdapter;

    @GetMapping(value = "/all")
    @ApiOperation(value = "Get All Visitors", response = Iterable.class)
    public List<NightClubVisitor> getAllVisitors() throws NotFoundException {

        List<NightClubVisitor> visitorList = nightClubVisitorService.getAllVisitors();

        if (visitorList.isEmpty()) {
            String message = "No one Visitor is found";
            logger.error(message);
            throw new NotFoundException(message);
        }

        return visitorList;
    }

    @GetMapping(value = "/all-active")
    @ApiOperation(value = "Get Only Active Visitors", response = Iterable.class)
    public List<NightClubVisitor> getAllActiveVisitors() throws NotFoundException {

        List<NightClubVisitor> activeVisitorList = nightClubVisitorService.getAllActiveVisitors();

        if (activeVisitorList.isEmpty()) {
            String message = "No one Active Visitor is found";
            logger.error(message);
            throw new NotFoundException(message);
        }

        return activeVisitorList;
    }

    @PostMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create a new Visitor. Add requestParam = 'create'", response = NightClubVisitor.class)
    @ApiParam(name = "RequestParam", type = "String", value = "create", defaultValue = "create", required = true)
    public RedirectView createVisitor(@RequestBody NightClubVisitor nightClubVisitor, @RequestParam String requestParam, HttpServletResponse response, BindingResult error) throws IOException {

        if (error.hasErrors() || !requestParam.contains("create")) {
            logger.error(error.toString());
            throw new BadRequestException(error.toString());
        }

        if (StringUtils.isBlank(nightClubVisitor.getUsername())) {
            String message = nightClubVisitor.getId() == null ? "Visitor Name is required !" : "Visitor Name is required ! : " + nightClubVisitor.getId();
            logger.error(message);
            throw new BadRequestException(message);
        }

        if (StringUtils.isBlank(nightClubVisitor.getEmail())) {
            String message = nightClubVisitor.getEmail() == null ? "Visitor Email is required !" : "Visitor Email is required ! : " + nightClubVisitor.getId();
            logger.error(message);
            throw new BadRequestException(message);
        }

        if (StringUtils.isBlank(nightClubVisitor.getPassword())) {
            String message = "Please, set Password !";
            logger.error(message);
            throw new BadRequestException(message);
        }

        if (nightClubVisitor.getId() != null) {
            if (nightClubVisitorService.checkAvoidDoubleEmail(nightClubVisitor.getEmail(), nightClubVisitor.getId())) {
                String message = "Visitor with email: " + nightClubVisitor.getEmail() + " exists. Please, set another email";
                logger.error(message);
                throw new BadRequestException(message);
            }
        } else {

            if (nightClubVisitorService.checkAvoidDoubleEmailByEmail(nightClubVisitor.getEmail())) {

                String message = "Visitor with email: " + nightClubVisitor.getEmail() + " exists. Please, set another email";
                logger.error(message);
                throw new BadRequestException(message);
            }
        }

        try {

            NightClubVisitor createNightClubVisitor = new NightClubVisitor();
            createNightClubVisitor.setUsername(nightClubVisitor.getUsername().toUpperCase());
            createNightClubVisitor.setEmail(nightClubVisitor.getEmail().toLowerCase());
            createNightClubVisitor.setPassword(StringUtils.isEmpty(nightClubVisitor.getPassword()) ? nightClubVisitor.getPassword() : customWebSecurityConfigurerAdapter.passwordEncoder().encode(nightClubVisitor.getPassword()));
            createNightClubVisitor.setRoles(Collections.singleton(RoleType.USER));
            nightClubVisitorService.saveOrUpdateNightClubVisitor(createNightClubVisitor);

        } catch (Exception e) {
            String message = "Can't create a new Visitor. " + e.getMessage();
            logger.error(message);
            e.printStackTrace();
        }

        logger.debug("Was created a new Visitor: " + nightClubVisitor);

        return new RedirectView("/all");
    }

    @PostMapping("edit")
    @ApiOperation(value = "Edit a Visitor. Add requestParam = 'edit'", response = Iterable.class)
    @ApiParam(name = "RequestParam", type = "String", value = "edit", defaultValue = "edit", required = true)
    public RedirectView editVisitor(@RequestBody NightClubVisitor nightClubVisitor, @RequestParam String requestParam, HttpServletResponse response, BindingResult error) throws IOException {

        if (error.hasErrors() || nightClubVisitor.getId() == null) {
            logger.error(error.toString());
            throw new BadRequestException(error.toString());
        }

       /* if (StringUtils.isBlank(nightClubVisitor.getEmail())) {
            String message = nightClubVisitor.getEmail() == null ? "Visitor Email is required !" : "Visitor Email is required ! : " + nightClubVisitor.getId();
            logger.error(message);
            throw new BadRequestException(message);
        }*/

        if (requestParam.contains("edit") && nightClubVisitor.getId() != null) {
            if (nightClubVisitor.getEmail() != null) {
                if (nightClubVisitorService.checkAvoidDoubleEmail(nightClubVisitor.getEmail(), nightClubVisitor.getId())) {
                    String message = "Visitor with email: " + nightClubVisitor.getEmail() + " exists. Please, set another email";
                    logger.error(message);
                    throw new BadRequestException(message);
                }
            } else {

                try {

                    NightClubVisitor editNightClubVisitor = nightClubVisitorService.getNightClubVisitorById(nightClubVisitor.getId());
                    editNightClubVisitor.setUsername(StringUtils.isNotBlank(nightClubVisitor.getUsername()) ? nightClubVisitor.getUsername() : editNightClubVisitor.getUsername());
                    editNightClubVisitor.setEmail(StringUtils.isNotBlank(nightClubVisitor.getEmail()) ? nightClubVisitor.getEmail() : editNightClubVisitor.getEmail());
                    editNightClubVisitor.setPassword(StringUtils.isNotBlank(nightClubVisitor.getPassword()) ? nightClubVisitor.getPassword() : customWebSecurityConfigurerAdapter.passwordEncoder().encode(editNightClubVisitor.getPassword()));
                    nightClubVisitorService.saveOrUpdateNightClubVisitor(editNightClubVisitor);

                } catch (Exception e) {
                    String message = "Can't update a new Visitor with id: " + nightClubVisitor.getId() + " " + e.getMessage();
                    logger.error(message);
                    e.printStackTrace();
                }

                logger.trace("Was updated Visitor: " + nightClubVisitor.getId());

            }
        }

        return new RedirectView("/all");
    }


    @GetMapping(value = "/visited/{id}")
    @ApiOperation(value = "Get All Nigh Club Visitors Who Visited NightClub by Club Id", response = Iterable.class)
    public List<NightClubVisitor> getAllNightClubVisitorsWhoVisitedNightClub(@PathVariable Long id) throws NotFoundException {

        List<NightClubVisitor> nightClubVisitorList = nightClubVisitorService.getAllNightClubVisitorsWhoVisitedNightClub(id);

        if (nightClubVisitorList.isEmpty()) {
            String message = "No one Night Club is visited";
            logger.error(message);
            throw new NotFoundException(message);
        }

        return nightClubVisitorList;
    }


}

