package com.example.chapter03.dao;

import com.example.chapter03.bo.UserInfo;
import org.springframework.data.repository.query.Param;

/**
 * @author donghuaqiang
 * @Title: TOTO
 * @Description: TOTO
 * @date 19-2-26 17:23
 */
public interface UserMapperCustom {
    UserInfo getUserFromDatabase(@Param("username") String username);
}
