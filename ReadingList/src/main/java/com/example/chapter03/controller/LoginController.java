package com.example.chapter03.controller;

import com.example.chapter03.entity.User;
import com.example.chapter03.service.LoginService;
import com.example.chapter03.service.UserService;
import com.example.utils.token.TokenDetail;
import com.example.utils.token.TokenDetailImpl;
import com.example.utils.token.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by YanMing on 2017/3/14.
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    LoginService loginService;

    @Autowired
    TokenUtils tokenUtils;

    @RequestMapping(method = RequestMethod.POST,produces = MediaType.TEXT_PLAIN_VALUE)
    public String login(@RequestBody @Valid User user) {
        if (user == null || StringUtils.isEmpty(user.getUsername()) || StringUtils.isEmpty(user.getPassword())) {
            return null;
        }
        boolean ok = loginService.isOk(user);
        if(!ok){
            return null;
        }

        TokenDetail tokenDetail = new TokenDetailImpl(user.getUsername());
        String token = tokenUtils.generateToken(tokenDetail);

        return token;
    }

}
