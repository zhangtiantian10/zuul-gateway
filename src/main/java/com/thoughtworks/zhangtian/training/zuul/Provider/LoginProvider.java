package com.thoughtworks.zhangtian.training.zuul.Provider;

import com.thoughtworks.zhangtian.training.zuul.dto.User;
import com.thoughtworks.zhangtian.training.zuul.tools.TokenGenerate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
public class LoginProvider implements FallbackProvider {

    @Autowired
    private TokenGenerate tokenGenerate;

    @Override
    public String getRoute() {
        return "login-service";
    }

    @Override
    public ClientHttpResponse fallbackResponse() {
        return null;
    }

    private ClientHttpResponse response(final HttpStatus status, User user) {
        return new ClientHttpResponse() {

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                return headers;
            }

            @Override
            public InputStream getBody() throws IOException {
                return new ByteArrayInputStream(tokenGenerate.getToken(user).getBytes("UTF-8"));
            }

            @Override
            public HttpStatus getStatusCode() throws IOException {
                return status;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return status.value();
            }

            @Override
            public String getStatusText() throws IOException {
                return status.getReasonPhrase();
            }

            @Override
            public void close() {

            }
        };
    }

    @Override
    public ClientHttpResponse fallbackResponse(Throwable cause) {
        User user = new User(1, "jianpan2", "123456");
        return response(HttpStatus.OK, user);
    }
}
