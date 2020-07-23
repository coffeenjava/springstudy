package com.brian.springstudy.controller;

import com.brian.springstudy.controller.dto.UserDto;
import com.brian.springstudy.service.UserService;
import com.brian.springstudy.support.enums.YesNo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/enum")
    public void setWithRequestParam(@RequestParam YesNo deleteYn) {
        System.out.println(deleteYn);
    }

    @PutMapping
    public void setWithRequestBody(@RequestBody UserDto dto) {
        System.out.println(dto.getUserGrade());
    }

    @GetMapping("/enum")
    public YesNo getEnumWithResponseBody() {
        return YesNo.YES;
    }

    @GetMapping
    public UserDto getDtoWithResponseBody() {
        return UserDto.getSample();
    }

    @PostMapping
    public Long saveJpaEntity(@RequestBody UserDto dto) {
        return userService.save(dto);
    }

    @GetMapping("/{userId}")
    public UserDto getJpaEntity(@PathVariable Long userId) {
        return userService.findOne(userId);
    }
}
