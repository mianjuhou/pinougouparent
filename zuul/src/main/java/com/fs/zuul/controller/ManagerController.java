package com.fs.zuul.controller;

import com.fs.common.entity.Result;
import com.fs.common.entity.StatusCode;
import com.fs.common.util.JwtUtil;
import com.fs.zuul.bean.LoginConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/manager")
@CrossOrigin
public class ManagerController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private LoginConfiguration loginConfig;

    @PostMapping("/login")
    public Result login(@RequestBody Map<String, String> map) {
        String username = map.get("username");
        String password = map.get("password");
        Map loginConfigMap = loginConfig.getMap();
        if (loginConfigMap.containsKey(username)) {
            if (loginConfigMap.get(username).equals(password)) {
                String token = jwtUtil.createJWT(username, username, "admin");
                Map<String, String> retMap = new HashMap<>();
                retMap.put("username", username);
                retMap.put("token", token);
                retMap.put("roles", "admin");
                return new Result(true, StatusCode.OK, "登录成功", retMap);
            }
        }
        return new Result(false, StatusCode.ERROR, "登录失败，用户名或密码错误");
    }

}
