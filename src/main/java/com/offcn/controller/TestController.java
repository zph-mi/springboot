package com.offcn.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class TestController {

    //set
    @RequestMapping("/setValue")
    public void setValue(HttpSession session){
        session.setAttribute("message","hello session");
    }

    //get
    @RequestMapping("/getValue")
    public String getValue(HttpSession session){
        return (String)session.getAttribute("message");
    }


}
