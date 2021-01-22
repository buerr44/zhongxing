package com.vw.zhongxing.web;

import com.vw.zhongxing.model.UserDO;
import com.vw.zhongxing.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @author Buer44
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @RequestMapping("/login")
    public String login(String username,String password){
        String result = "";

        UserDO user = userService.login(username,password);
        if(user != null){
            result = "main.html";
        }else{
            result = "index.html";
        }

        return result;
    }
}
