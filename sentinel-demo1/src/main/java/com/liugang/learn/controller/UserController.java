package com.liugang.learn.controller;

import com.liugang.learn.model.User;
import com.liugang.learn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {


    @Autowired
    private UserService userService;

    @GetMapping("/getUser")
    @ResponseBody
    public User getUser(@RequestParam("uid")Long uid){
        return userService.getUser(uid);
    }
}
