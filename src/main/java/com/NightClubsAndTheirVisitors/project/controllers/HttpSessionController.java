package com.NightClubsAndTheirVisitors.project.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

@RestController
public class HttpSessionController {

    @GetMapping("/")
    public void getSession(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();
        Integer count = (Integer)session.getAttribute("count");

      /*  NightClubVisitor nightClubVisitor = (NightClubVisitor) session.getAttribute("user");
        if (nightClubVisitor==null){
               nightClubVisitor= new NightClubVisitor();
        }
        else {

        }*/


        Cookie cookie = new Cookie("UNKNOWN_USER_ID", UUID.randomUUID().toString());
        cookie.setMaxAge(7 * 24 * 60 * 60); // expires in 7 days
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/"); // global cookie accessible every where
//add cookie to response
        response.addCookie(cookie);

        if (count==null)
        {
            session.setAttribute("count", 1);

            count=1;
        } else {
            session.setAttribute("count", count+1);
        }

        PrintWriter printWriter = response.getWriter();
        printWriter.println("<html>");
        printWriter.println("<h1> Your count is: " + count+ "</h1>");
        printWriter.println("</html>");

    }

   // private final HttpSessionBean httpSessionBean;

    /*@Autowired
    public HttpSessionController(HttpSessionBean httpSessionBean) {
        this.httpSessionBean = httpSessionBean;
    }


    @GetMapping(path = "/controller")
    public String example(String name){
        if(!StringUtils.isEmpty(name)){
            httpSessionBean.setName(name);
            return "New name have been received - " + name;
        }else if (!StringUtils.isEmpty(httpSessionBean.getName())){
            return "Current name: " + httpSessionBean.getName();
        }else {
            return "There is no saved name";
        }
    }*/

}
