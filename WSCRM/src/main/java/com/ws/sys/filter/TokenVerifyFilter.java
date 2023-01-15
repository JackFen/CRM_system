package com.ws.sys.filter;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ws.common.constant.SystemConstant;
import com.ws.common.result.JWTUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TokenVerifyFilter extends BasicAuthenticationFilter {
    public TokenVerifyFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    /**
     * 校验提交的Token是否合法的方法
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        //获取请求携带的Token信息
        String header = request.getHeader("Authorization");
        if(header!=null && header.startsWith(SystemConstant.SYS_TOKEN_PREFIX)){
            //传递了token信息，同时有我们添加的对应的前缀
            //1.获取到正常的token
            String token = header.replace(SystemConstant.SYS_TOKEN_PREFIX, "");
            //2.校验token信息是否合法
            DecodedJWT verify = JWTUtils.verify(token);
            if (verify == null){
                //说明校验失败 -- 给用户提示 请先登录
                responseLogin(response);
            }
            //走到这说明是正常的
            String userName = verify.getClaim("username").asString();
            //放过请求 后续的控制器可能需要相关的权限
            List<GrantedAuthority> list= new ArrayList<>();
            list.add(new SimpleGrantedAuthority("ADMIN"));
            //根据账号获取相关的权限
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userName,"",list);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            //放过请求
            chain.doFilter(request,response);
        }
        else {
            //没有携带Token信息，或者是非法的请求
            responseLogin(response);
        }
    }

    private static void responseLogin(HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter writer = response.getWriter();
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("code",HttpServletResponse.SC_FORBIDDEN);
        resultMap.put("msg","请先登录!");
        writer.write(JSON.toJSONString(resultMap));
        writer.flush();
        writer.close();
    }
}
