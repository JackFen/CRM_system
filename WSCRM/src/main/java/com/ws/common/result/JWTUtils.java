package com.ws.common.result;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT操作的工具类
 */
public class JWTUtils {
    private static final String SECRET="jack123";

    /**
     * 生成token信息
     * @return
     */
    public static String getToken(Map<String,String> map){
        JWTCreator.Builder builder=JWT.create();
        //设置payLoad
        map.forEach((k,v)->{
            builder.withClaim(k,v);
        });
        //设置过期时间
        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.DATE,7);//默认的过期时间是7天
        Map<String, Object> header = new HashMap<>();
        header.put("alg","HS256");
        header.put("typ","JWT");
        return builder.withHeader(header)
                .withExpiresAt(calendar.getTime())
                .sign(Algorithm.HMAC256(SECRET));
    }

    /**
     * 校验token是否合法
     *      如果非法会抛出相关的异常信息
     *      如果合法的就会返回DecodedJWT对象
     * @param token
     * @return
     */
    public static DecodedJWT verify(String token){
        //如果不抛出异常说明验证通过，否则验证失败
        DecodedJWT verify = null;
        try{
            verify=JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token);
        }catch (SignatureVerificationException e){
            e.printStackTrace();
        }catch (AlgorithmMismatchException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return verify;
    }
}
