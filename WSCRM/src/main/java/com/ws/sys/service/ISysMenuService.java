package com.ws.sys.service;

import com.ws.common.util.PageUtils;
import com.ws.sys.entity.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ws.sys.model.SysMenuQueryDTO;

import java.util.List;

/**
 * <p>
 * 菜单管理 服务类
 * </p>
 *
 * @author jack
 * @since 2023-01-17
 */
public interface ISysMenuService extends IService<SysMenu> {

    PageUtils listPage(SysMenuQueryDTO dto);

    List<SysMenu> listParent();

    void saveOrUpdateMenu(SysMenu menu);

    SysMenu queryMenuById(Long menuId);
}
