package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class LoginTemplate {

    @RequestMapping("")
    public String home(){
        return "../static/index";
    }
    @RequestMapping("login")
    public String login(){
        return "login";
    }

    @RequestMapping("/courses")
    public String showCourses(){
        return "courses";
    }

}
