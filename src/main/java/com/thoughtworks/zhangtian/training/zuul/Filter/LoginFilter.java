package com.thoughtworks.zhangtian.training.zuul.Filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.thoughtworks.zhangtian.training.zuul.dto.User;
import com.thoughtworks.zhangtian.training.zuul.tools.TokenGenerate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.POST_TYPE;

@Component
public class LoginFilter extends ZuulFilter {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TokenGenerate tokenGenerate;

    @Override
    public String filterType() {
        return POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return  0;
    }

    @Override
    public boolean shouldFilter() {
        return RequestContext.getCurrentContext().getRequest().getRequestURI().equals("/api/login")
                && RequestContext.getCurrentContext().getResponse().getStatus() == 200;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        InputStream stream = ctx.getResponseDataStream();
        try {
            String body = StreamUtils.copyToString(stream, Charset.forName("UTF-8"));
            User user = objectMapper.readValue(body, User.class);
            @SuppressWarnings("unchecked")
            String token = tokenGenerate.getToken(user);

            Map<String, String> result = new HashMap<>();
            result.put("token", token);

            ctx.setResponseBody(objectMapper.writeValueAsString(result));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return ctx;
    }
}
