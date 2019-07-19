package com.fs.securitydemo.controller;

import com.fs.securitydemo.bean.MyConfiguration;
import com.fs.securitydemo.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MyConfiguration myConfiguration;


    @RequestMapping("/index/{pwd}")
    public String index(@PathVariable("pwd") String rawPwd) {
        String enPwd = encoder.encode(rawPwd);

//        jwtUtil.createJWT("", "", "");

        return enPwd;
    }

    @RequestMapping("/login")
    public String login(String rawPwd, String enPwd) {
        boolean matches = encoder.matches(rawPwd, enPwd);
//        Claims claims = jwtUtil.parseJWT("");
        return "login:" + matches;
    }

    @RequestMapping("/goods")
    public String goods() {
        System.out.println(myConfiguration.getName());
        System.out.println(myConfiguration.getMap());
        System.out.println(myConfiguration.getMap().containsKey("value1"));
        return "goods";
    }
}
