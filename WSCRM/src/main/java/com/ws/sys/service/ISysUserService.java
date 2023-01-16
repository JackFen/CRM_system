package com.ws.sys.service;

import com.ws.common.util.PageUtils;
import com.ws.sys.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ws.sys.model.SysUserQueryDTO;

import java.util.List;

/**
 * <p>
 * 系统用户 服务类
 * </p>
 *
 * @author jack
 * @since 2023-01-09
 */
public interface ISysUserService extends IService<SysUser> {

    List<SysUser> queryByUserName(String username);

    PageUtils queryPage(SysUserQueryDTO dto);

    boolean checkUserName(String username);

    void saveOrUpdateUser(SysUser sysUser);

    SysUser queryByUserId(Long userId);
}
