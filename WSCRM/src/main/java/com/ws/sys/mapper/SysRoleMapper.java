package com.ws.sys.mapper;

import com.ws.sys.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 角色 Mapper 接口
 * </p>
 *
 * @author jack
 * @since 2023-01-09
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    int checkRoleCanDelete(Long roleId);
}
