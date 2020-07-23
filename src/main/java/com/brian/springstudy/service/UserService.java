package com.brian.springstudy.service;

import com.brian.springstudy.controller.dto.UserDto;
import com.brian.springstudy.entity.User;
import com.brian.springstudy.entity.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository repository;

    public Long save(UserDto dto) {
        User user = new User();
        BeanUtils.copyProperties(dto,user);
        repository.saveAndFlush(user);
        return user.getUserId();
    }

    public UserDto findOne(Long userId) {
        User user = repository.findById(userId).get();
        UserDto dto = new UserDto();
        BeanUtils.copyProperties(user,dto);
        return dto;
    }
}
