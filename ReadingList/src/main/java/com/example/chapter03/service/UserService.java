package com.example.chapter03.service;

import com.example.chapter03.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserService{

    int insert(User user);

    int insertSelective(User user);

    int insertList(List<User> users);

    int updateByPrimaryKeySelective(User user);
}
