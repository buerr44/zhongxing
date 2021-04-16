package com.fawvw.cloud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author HuangGang
 * @create 2021-01-27 15:28
 **/
@Controller
public class BasicController {

    @RequestMapping("/showMain")
    public String showMain(){
        return "main";
    }

    @RequestMapping("/showLogin")
    public String showLogin(){
        return "index";
    }

    @RequestMapping("/showDataCenter")
    public String showDataCenter(){
        return "dataCenter";
    }

    @RequestMapping("/showCourse")
    public String showCourse(){
        return "course";
    }

    @RequestMapping("/showStructure")
    public String showStructure(){
        return "structure";
    }

    @RequestMapping("/showFeedback")
    public String showFeedback(){
        return "feedback";
    }

    @RequestMapping("/showEmployeeManage")
    public String showEmployeeManage(){
        return "employeeManage";
    }

    @RequestMapping("/showUser")
    public String showUser(){
        return "user";
    }

    @RequestMapping("/showStudyPlan")
    public String showStudyPlan(){
        return "studyPlan";
    }

    @RequestMapping("/showFlowerPic")
    public String showFlowerPic(){
        return "flowerPic";
    }

    @RequestMapping("/showFlowerWord")
    public String showFlowerWord(){
        return "flowerWord";
    }

    @RequestMapping("/showBanner")
    public String showBanner(){
        return "banner";
    }
}
