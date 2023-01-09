package com.ws.sys.service.impl;

import com.ws.sys.entity.SysUser;
import com.ws.sys.mapper.SysUserMapper;
import com.ws.sys.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统用户 服务实现类
 * </p>
 *
 * @author jack
 * @since 2023-01-09
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

}
