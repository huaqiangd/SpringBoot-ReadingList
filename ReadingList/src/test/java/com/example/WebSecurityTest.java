/**
 * @Title: TOTO
 * @Description: TOTO
 * @author donghuaqiang
 * @date 19-2-27 17:09
 */

package com.example;

import com.example.chapter03.bo.UserInfo;
import com.example.chapter03.dao.UserMapperCustom;
import com.example.chapter03.entity.User;
import com.example.utils.token.TokenUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.Date;


public class WebSecurityTest extends BasicTest {
    @Autowired
    TestRestTemplate restTemplate;
    @Autowired
    TokenUtils tokenUtils;
    @Autowired
    private UserMapperCustom userMapper;

    @Test
    public void testGetUser() {
        UserInfo user = userMapper.getUserFromDatabase("admin");
        System.out.println(user);
    }

    @Test
    public void testLogin() {
        User user = new User();
        user.setUsername("admin");
        user.setPassword("123456");
        ResponseEntity<String> httpResp = restTemplate.postForEntity("/login", user, String.class);
        System.out.println(httpResp.getBody());
        System.out.println(httpResp.getHeaders());
        System.out.println(httpResp.getStatusCode());
        if (httpResp.getBody() != null) {
            System.out.println("token:" + httpResp.getBody());
            String usernameFromToken = tokenUtils.getUsernameFromToken(httpResp.getBody());
            System.out.println("username:" + usernameFromToken);
            Date createdDateFromToken = tokenUtils.getCreatedDateFromToken(httpResp.getBody());
            System.out.println("createTime:" + createdDateFromToken);
            Date expirationDateFromToken = tokenUtils.getExpirationDateFromToken(httpResp.getBody());
            System.out.println("expirationTime" + expirationDateFromToken);

        }
    }
}
