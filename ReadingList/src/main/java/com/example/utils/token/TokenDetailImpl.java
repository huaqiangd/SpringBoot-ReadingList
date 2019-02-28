/**
 * @Title: TOTO
 * @Description: TOTO
 * @author donghuaqiang
 * @date 19-2-26 15:50
 */

package com.example.utils.token;

public class TokenDetailImpl implements TokenDetail {
    private final String username;

    public TokenDetailImpl(String username) {
        this.username = username;
    }

    @Override
    public String getUsername() {
        return this.username;
    }
}
