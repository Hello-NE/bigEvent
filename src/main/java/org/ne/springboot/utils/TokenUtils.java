package org.ne.springboot.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.ne.springboot.pojo.User;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TokenUtils {

    private static final long EXPIRE_TIME = 10 * 60 * 60 * 1000 * 24 ;
    private static final String TOKEN_SECRET = "WNE20040822";  //密钥盐

    /**
     * 签名生成
     *
     * @param user
     * @return
     */
    public static String sign(User user) {
        String token = null;
        try {
            Date expiresAt = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            token = JWT.create()
                    .withIssuer("auth0")
                    .withClaim("userId", user.getId().toString())
                    .withClaim("username", user.getUsername())
                    .withExpiresAt(expiresAt)
                    // 使用了HMAC256加密算法。
                    .sign(Algorithm.HMAC256(TOKEN_SECRET));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;
    }

    /**
     * 签名验证
     *
     * @param token
     * @return
     */
    public static boolean verify(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).withIssuer("auth0").build();
            DecodedJWT jwt = verifier.verify(token);
            System.out.println("认证通过：");
            System.out.println("userId: " + jwt.getClaim("userId").asString());
            System.out.println("username: " + jwt.getClaim("username").asString());
            System.out.println("过期时间：      " + jwt.getExpiresAt());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 管理员认证
     *
     * @param token
     * @return
     */
    public static boolean adminVerify(String token) {


        try {

            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).withIssuer("auth0").build();
            DecodedJWT jwt = verifier.verify(token);
            if ("admin".equals(jwt.getClaim("username").asString())) {
                System.out.println("管理员认证通过");
                return true;
            } else {
                System.out.println("管理员认证未通过");
            }
            return false;

        } catch (Exception e) {
            return false;
        }
    }


    /**
     * 接受token，返回业务数据
     *
     * */
    public static Map<String, Object> parseToken(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).withIssuer("auth0").build();
        DecodedJWT jwt = verifier.verify(token);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", jwt.getClaim("userId").asString());
        map.put("username", jwt.getClaim("username").asString());
        map.put("isAdmin", jwt.getClaim("admin").asBoolean());

        return map;
    }

}
