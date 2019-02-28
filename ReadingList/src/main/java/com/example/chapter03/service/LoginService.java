/**
 * @Title: TOTO
 * @Description: TOTO
 * @author donghuaqiang
 * @date 19-2-28 15:52
 */

package com.example.chapter03.service;

import com.example.chapter03.dao.UserMapper;
import com.example.chapter03.entity.User;
import com.example.chapter03.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    UserRepository userRepository;

    /**
     * 判断 用户 的账号密码 是否正确
     * @param user
     * @return
     */
    public boolean isOk(User user) {
        User one = userRepository.findOne(user.getUsername());
        // return one != null && one.getEnable() == 1 && one.getPassword().equals(user.getPassword());
        return one != null &&  one.getPassword().equals(user.getPassword());
    }
}
