package com.ws.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ws.common.util.PageUtils;
import com.ws.sys.entity.SysRole;
import com.ws.sys.mapper.SysRoleMapper;
import com.ws.sys.model.SysRoleQueryDTO;
import com.ws.sys.service.ISysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色 服务实现类
 * </p>
 *
 * @author jack
 * @since 2023-01-09
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    /**
     * 分页查询
     * @param queryDTO 查询对象
     * @return 分页结果
     */
    @Override
    public PageUtils queryPage(SysRoleQueryDTO queryDTO) {
        QueryWrapper<SysRole> wrapper=new QueryWrapper<SysRole>().like(
                StringUtils.isNotEmpty(queryDTO.getRoleName()),"rolename",queryDTO.getRoleName()
        );
        Page<SysRole> page = this.page(queryDTO.page(), wrapper);
        return new PageUtils(page);
    }

    @Override
    public void saveRole(SysRole role) {

    }

    @Override
    public void update(SysRole role) {

    }

    @Override
    public void deleteBatch(Long[] roleIds) {

    }
}
