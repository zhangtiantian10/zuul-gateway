package com.thoughtworks.zhangtian.training.zuul.tools;

import com.thoughtworks.zhangtian.training.zuul.dto.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

@Component
public class TokenGenerate {
    @Value("${private.password}")
    private String privatePassword;

    public String getToken(User user) throws UnsupportedEncodingException {
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("name", user.getName());
        claims.put("id", user.getId());

        return Jwts.builder()
                .addClaims(claims)
                .signWith(SignatureAlgorithm.HS512, privatePassword.getBytes("UTF-8"))
                .compact();
    }
}
