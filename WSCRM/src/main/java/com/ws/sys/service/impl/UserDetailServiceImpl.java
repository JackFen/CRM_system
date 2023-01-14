package com.ws.sys.service.impl;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 认证校验的方法
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {
    /**
     * 完成账号的校验
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if ("admin".equals(username)){
            //绑定当前用户对应的角色
            List<GrantedAuthority> list= new ArrayList<>();
            list.add(new SimpleGrantedAuthority("ADMIN"));
            //密码模拟的就是数据库中查询出来的
            UserDetails userDetails = new User("admin","$2a$10$qc40w21YiaRtxuvp9Vd1e.m3al.WoH2GcwHClcA/LtkMILdukFcN6",list);
            return userDetails;
        }
        return null;
    }
}
