package com.ws.sys.service;

import com.ws.common.util.PageUtils;
import com.ws.sys.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ws.sys.model.SysRoleQueryDTO;
import org.apache.ibatis.annotations.Param;


/**
 * <p>
 * 角色 服务类
 * </p>
 *
 * @author jack
 * @since 2023-01-09
 */
public interface ISysRoleService extends IService<SysRole> {
    PageUtils queryPage(SysRoleQueryDTO queryDTO);
    void saveOrUpdateRole(SysRole role);
    void update(SysRole role);
    void deleteBatch(Long[] roleIds);

    boolean checkRoleName(String roleName);

    boolean deleteRoleById(@Param("roleId") Long roleId);
}
