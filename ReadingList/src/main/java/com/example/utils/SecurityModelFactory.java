/**
 * @Title: TOTO
 * @Description: TOTO
 * @author donghuaqiang
 * @date 19-2-27 9:31
 */

package com.example.utils;

import com.example.chapter03.bo.UserInfo;
import com.example.utils.userdetails.UserDetailsImpl;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Collection;

public class SecurityModelFactory {

    private SecurityModelFactory() {
    }

    public static UserDetailsImpl create(UserInfo userInfo) {
        Collection<? extends GrantedAuthority> authorities;
        try {
            authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(userInfo.getAuthorities());
        } catch (Exception e) {
            authorities = null;
        }

        return new UserDetailsImpl(
                userInfo.getUsername(),
                userInfo.getPassword(),
                userInfo.getLastPasswordChange(),
                userInfo.getEnable(),
                authorities);
    }

}
