package com.example.chapter03.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collection;

/**
 * @author HuaQiangD
 * @Title: TOTO
 * @Description: TOTO
 * @date 19-2-21 18:53
 */
@Entity(name = "readinglist_user")
@Data
public class User {

    @Id
    private String username;
    private String password;
    private Integer roleId;
    private Timestamp lastPasswordChange;
    private Integer enable;

}
