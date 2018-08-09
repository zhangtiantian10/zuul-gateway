package com.thoughtworks.zhangtian.training.zuul.feign;

import com.thoughtworks.zhangtian.training.zuul.dto.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "user-service")
public interface UserFeign {
    @PostMapping("/validate")
    User validateUser(User user);

    @PostMapping("/login")
    User login(User user);
}
