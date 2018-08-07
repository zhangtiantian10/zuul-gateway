package com.thoughtworks.zhangtian.training.zuul.controller;

import com.thoughtworks.zhangtian.training.zuul.dto.User;
import com.thoughtworks.zhangtian.training.zuul.feign.UserFeign;
import com.thoughtworks.zhangtian.training.zuul.tools.TokenGenerate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {
    @Autowired
    private UserFeign userFeign;

    @Autowired
    private TokenGenerate tokenGenerate;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody User user) throws UnsupportedEncodingException {
        User loginUser = userFeign.login(user);
        if (loginUser != null) {
            String token = tokenGenerate.getToken(loginUser);
            Map<String, String> result = new HashMap<>();
            result.put("token", token);
            return ResponseEntity.ok(result);
        }

        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}
