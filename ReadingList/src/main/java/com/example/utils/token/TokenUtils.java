/**
 * @author donghuaqiang
 * @date 19-2-26 15:46
 * @Title: TOTO
 * @Description: 这个工具类的目前做的事情是:
 * <p>
 * 把用户名封装进 token 的主体 claims 中，并在里面封装了当前时间（方便后面判断 token 是否在修改密码之前生成的）
 * 再计算 token 过期的时间写入到 轮子的 token 中
 * 对 轮子的 token 进行撒盐加密，生成一串字符串，即我们定制的 token
 * <p>
 * 作者：wean_a23e 链接：https://www.jianshu.com/p/4468a2fff879 来源：简书 简书著作权归作者所有，任何形式的转载都请联系作者获得授权并注明出处。
 */

package com.example.utils.token;

import com.example.utils.userdetails.UserDetailsImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenUtils {
    private final Logger logger = Logger.getLogger(this.getClass());

    @Value("${token.secret}")
    private String secret;

    @Value("${token.expiration}")
    private Long expiration;

    /**
     * 根据 TokenDetail 生成 Token
     *
     * @param tokenDetail
     * @return
     */
    public String generateToken(TokenDetail tokenDetail) {
        Map<String, Object> claims = new HashMap<String, Object>();
        claims.put("sub", tokenDetail.getUsername());
        claims.put("created", this.generateCurrentDate());
        return this.generateToken(claims);
    }

    /**
     * 根据 claims 生成 Token
     *
     * @param claims
     * @return
     */
    private String generateToken(Map<String, Object> claims) {
        try {
            return Jwts.builder()
                    .setClaims(claims)
                    .setExpiration(this.generateExpirationDate())
                    .signWith(SignatureAlgorithm.HS512, this.secret.getBytes("UTF-8"))
                    .compact();
        } catch (UnsupportedEncodingException ex) {
            //didn't want to have this method throw the exception, would rather log it and sign the token like it was before
            logger.warn(ex.getMessage());
            return Jwts.builder()
                    .setClaims(claims)
                    .setExpiration(this.generateExpirationDate())
                    .signWith(SignatureAlgorithm.HS512, this.secret)
                    .compact();
        }
    }

    /**
     * token 过期时间
     *
     * @return
     */
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + this.expiration * 1000);
    }

    /**
     * 获得当前时间
     *
     * @return
     */
    private Date generateCurrentDate() {
        return new Date(System.currentTimeMillis());
    }


    // 步骤三：实现验证 token 是否有效，并根据 token 获得账号详细信息（权限，是否处于封号状态）的功能
    /**
     * 分析实现的过程：在步骤二中，我们把用户的的 username 、 token 创建的时间 、 token 过期的时间封装到了加密过后的 token 字符串中，就是为了服务此时我们验证用户权限的目的。
     * 假设我们此时拿到了用户传递过来的一串 token，并且要根据这串 token 获得用户的详情可以这样做：
     * A. 尝试解析这串 token ，若成功解析出来，进入下一步，否则终止解析过程
     * B. 根据解析出来的 username 从数据库中查找用户的账号，最后一次密码修改的时间，权限，是否封号等用户详情信息，把这些信息封装到一个实体类中（userDetail类)。若查找不到该用户，终止解析进程
     * C. 检查 userDetail 中记录的封号状态，若是账号已被封号，返回封号结果，终止请求
     * D. 根据 userDtail 比较 token 是否处于有效期内，若不处于有效期内，终止解析过程，否则继续
     * E. 将 userDetail 中记录的用户权限写入本次请求会话中，解析完成。
     */


    /**
     * 从 token 中拿到 username
     *
     * @param token
     * @return
     */
    public String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = this.getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    /**
     * 解析 token 的主体 Claims
     *
     * @param token
     * @return
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(this.secret.getBytes("UTF-8"))
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    /**
     * 检查 token 是否处于有效期内
     *
     * @param token
     * @param userDetails
     * @return
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        UserDetailsImpl userDetailsImpl = (UserDetailsImpl) userDetails;
        final String username = this.getUsernameFromToken(token);
        final Date created = this.getCreatedDateFromToken(token);
        return (username.equals(userDetailsImpl.getUsername()) && !(this.isTokenExpired(token)) && !(this.isCreatedBeforeLastPasswordReset(created, userDetailsImpl.getLastPasswordChange())));
    }

    /**
     * 获得我们封装在 token 中的 token 创建时间
     *
     * @param token
     * @return
     */
    public Date getCreatedDateFromToken(String token) {
        Date created;
        try {
            final Claims claims = this.getClaimsFromToken(token);
            created = new Date((Long) claims.get("created"));
        } catch (Exception e) {
            created = null;
        }
        return created;
    }

    /**
     * 获得我们封装在 token 中的 token 过期时间
     *
     * @param token
     * @return
     */
    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = this.getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    /**
     * 检查当前时间是否在封装在 token 中的过期时间之后，若是，则判定为 token 过期
     *
     * @param token
     * @return
     */
    private Boolean isTokenExpired(String token) {
        final Date expiration = this.getExpirationDateFromToken(token);
        return expiration.before(this.generateCurrentDate());
    }

    /**
     * 检查 token 是否是在最后一次修改密码之前创建的（账号修改密码之后，则之前生成的 token 即使没过期也判断为无效）
     *
     * @param created
     * @param lastPasswordReset
     * @return
     */
    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }
}
