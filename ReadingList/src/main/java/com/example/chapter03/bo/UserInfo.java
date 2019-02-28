/**
 * @Title: TOTO
 * @Description: TOTO
 * @author donghuaqiang
 * @date 19-2-26 17:16
 */

package com.example.chapter03.bo;

import com.example.utils.login.LoginDetail;
import com.example.utils.token.TokenDetail;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserInfo implements LoginDetail, TokenDetail {

    //table:readinglist_reader
    private String username;
    private String password;
    private Timestamp lastPasswordChange;
    private Integer enable;
    //table:readinglist_role
    private String authorities;

    @Override
    public String getUsername() {
        return username;
    }
}
