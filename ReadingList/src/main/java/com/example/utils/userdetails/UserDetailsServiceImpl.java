/**
 * @Title: TOTO
 * @Description: TOTO
 * @author donghuaqiang
 * @date 19-2-26 16:16
 */

package com.example.utils.userdetails;

import com.example.chapter03.bo.UserInfo;
import com.example.chapter03.dao.UserMapperCustom;
import com.example.utils.SecurityModelFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

//实现 Spring Security 的一个 UserDetailService 接口，这个接口只有一个方法, loadUserByUsername
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapperCustom userMapper;

    /**
     * 获取 userDetail
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserInfo userInfo = userMapper.getUserFromDatabase(username);

        if (userInfo == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
            return SecurityModelFactory.create(userInfo);
        }
    }
}

