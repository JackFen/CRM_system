package com.ws;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * JWT演示
 */
@SpringBootTest
class WscrmApplicationTests {

    @Test
    void contextLoads() {

    }

    static String secret="jack";
    /**
     * 生成Token信息
     * head
     * payload
     * signature
     */
    @Test
    void generatorToken(){
        Map<String, Object> map = new HashMap<>();
        map.put("alg","HS256");
        map.put("typ","JWT");
        Calendar calendar= Calendar.getInstance();
        calendar.add(Calendar.SECOND,60);
        String token = JWT.create()
                .withHeader(map)//设置头部
                .withClaim("username", "userid")//设置payload
                .withExpiresAt(calendar.getTime())//设置过期时间 60秒
                .sign(Algorithm.HMAC256(secret));//设置签名secret 密钥 需要对外保密
        System.out.println(token);
        /*
        * 结果
        * eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9
        * .eyJleHAiOjE2NzM2ODgxMDksInVzZXJpZCI6MTAwMX0
        * .YvodpY2R9kbJ3itrQ6w5G5bRdqW_LAdIqtzt6c1s5VE
        */
    }

    /**
     * Token 的校验
     */
    @Test
    void verifier(){
        String token="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2NzM2ODg1MjQsInVzZXJpZCI6MTAwMX0.uCjk-E2Zn1feXzj-UbcrzceqESK6uAuIGTtuLKU4tA0";
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret)).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        System.out.println(decodedJWT.getClaim("userid").asInt());
    }

}
