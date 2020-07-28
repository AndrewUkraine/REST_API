package com.NightClubsAndTheirVisitors.project;

import com.NightClubsAndTheirVisitors.project.entities.NightClubVisitor;
import com.NightClubsAndTheirVisitors.project.entities.enums.RoleType;
import com.NightClubsAndTheirVisitors.project.services.impl.NightClubVisitorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.UUID;

@Component
public class MyBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

    @Autowired
    NightClubVisitorServiceImpl nightClubVisitorService;

    @Override
    public void commence(
            HttpServletRequest request, HttpServletResponse response, AuthenticationException authEx)
            throws IOException, ServletException {

        HttpSession session = request.getSession();

       /* if (session.getAttribute("user") == null)
        {
            NightClubVisitor nightClubVisitor = (NightClubVisitor) session.getAttribute("user");

            if (nightClubVisitor==null){

                nightClubVisitor.setEmail("andfd@gmail.com");
                nightClubVisitor.setUsername(UUID.randomUUID().toString());
                nightClubVisitor.setPassword("UNKNOWN_USER");
                nightClubVisitor.setRoles(Collections.singleton(RoleType.UNKNOWN_USER));
                nightClubVisitorService.saveOrUpdateNightClubVisitor(nightClubVisitor);
                session.setAttribute("user", nightClubVisitor);
            }

            Cookie cookie = new Cookie("UNKNOWN_USER_ID", UUID.randomUUID().toString());
            cookie.setMaxAge(7 * 24 * 60 * 60); // expires in 7 days
            cookie.setSecure(true);
            cookie.setHttpOnly(true);
            cookie.setPath("/"); // global cookie accessible every where
            response.addCookie(cookie);
        }

        else {
            response.addHeader("WWW-Authenticate", "Basic realm=" + getRealmName());
            response.setStatus(HttpServletResponse.SC_OK);
        }*/

        response.addHeader("WWW-Authenticate", "Basic realm=" + getRealmName());
        response.setStatus(HttpServletResponse.SC_OK);

        //response.sendRedirect(request.getServletPath());

        /*response.addHeader("WWW-Authenticate", "Basic realm=" + getRealmName());
        response.setStatus(HttpServletResponse.SC_ACCEPTED);
        response.sendRedirect(request.getServletPath());*/
       /* PrintWriter writer = response.getWriter();
        writer.println("HTTP Status 401 - " + authEx.getMessage());*/
    }

   @Override
    public void afterPropertiesSet() throws Exception {
        setRealmName("Baeldung");
        super.afterPropertiesSet();
    }
}
