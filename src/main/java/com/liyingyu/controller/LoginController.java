package com.liyingyu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by YingyuLi on 2018/1/31.
 */
@Controller
public class LoginController {
    @RequestMapping("/doLogin")
    public String doLogin(@RequestParam String name,@RequestParam String password){
        System.out.println("name="+name+", password="+password);
        System.out.println("###LoginController.doLogin()");
        return "success";
    }
    @RequestMapping("/login")
    public String login(){
        return "login";
    }
}
