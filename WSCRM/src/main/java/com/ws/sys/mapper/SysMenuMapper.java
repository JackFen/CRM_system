package com.ws.sys.mapper;

import com.ws.sys.entity.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 菜单管理 Mapper 接口
 * </p>
 *
 * @author jack
 * @since 2023-01-17
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    int canBeDeleted(@Param("menuId") Long menuId);
}
