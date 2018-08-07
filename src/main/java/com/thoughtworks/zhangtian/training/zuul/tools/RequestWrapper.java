package com.thoughtworks.zhangtian.training.zuul.tools;

import com.google.common.collect.ImmutableList;
import com.thoughtworks.zhangtian.training.zuul.dot.User;
import org.springframework.security.web.savedrequest.Enumerator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Enumeration;

public class RequestWrapper {
    public HttpServletRequest modifyHeaders(HttpServletRequest request, User user) {
        return new HttpServletRequestWrapper(request) {
            @Override
            public Enumeration<String> getHeaders(String name) {
                if (name.equals("authorization")) {
                    return new Enumerator<String>(ImmutableList.of(user.getId() + ":" + user.getName())) ;
                }

                return super.getHeaders(name);

            }
        };
    }
}
