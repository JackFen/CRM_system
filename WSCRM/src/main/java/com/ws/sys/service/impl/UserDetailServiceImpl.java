package com.ws.sys.service.impl;

import com.ws.sys.entity.SysUser;
import com.ws.sys.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    ISysUserService sysUserService;
    /**
     * 完成账号的校验
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //1.需要根据账号查询
        List<SysUser>list = sysUserService.queryByUserName(username);
        if (list!=null&&list.size()==1){
            //账号是存在的
            SysUser sysUser=list.get(0);
            //更加当前登录的账号查询到关联的角色信息
            List<GrantedAuthority> listRole= new ArrayList<>();
            listRole.add(new SimpleGrantedAuthority("ADMIN"));
            //密码模拟的就是数据库中查询出来的 密码是123456
            return new User(sysUser.getUsername(),sysUser.getPassword(),listRole);
        }
//        if ("admin".equals(username)){
//            //绑定当前用户对应的角色
//            List<GrantedAuthority> list= new ArrayList<>();
//            list.add(new SimpleGrantedAuthority("ADMIN"));
//            //密码模拟的就是数据库中查询出来的 密码是123456
//            UserDetails userDetails = new User("admin","$2a$10$qc40w21YiaRtxuvp9Vd1e.m3al.WoH2GcwHClcA/LtkMILdukFcN6",list);
//            return userDetails;
//        }
        return null;
    }
}
