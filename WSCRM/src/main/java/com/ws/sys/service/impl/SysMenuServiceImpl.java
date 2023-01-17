package com.ws.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ws.common.annotation.SystemLog;
import com.ws.common.util.PageUtils;
import com.ws.sys.entity.SysMenu;
import com.ws.sys.mapper.SysMenuMapper;
import com.ws.sys.model.SysMenuQueryDTO;
import com.ws.sys.model.SysUserQueryDTO;
import com.ws.sys.service.ISysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单管理 服务实现类
 * </p>
 *
 * @author jack
 * @since 2023-01-17
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

    /**
     * 查询所有的菜单信息
     * @param dto
     * @return
     */
    @Override
    public PageUtils listPage(SysMenuQueryDTO dto) {
        //先查询所有的一级菜单的数据，分页是针对一级菜单的数据分页
        QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",0)//查询所有的一级菜单
                .like(StringUtils.isNotBlank(dto.getName()),"name",dto.getName())
                .orderByAsc("order_num");
        Page<SysMenu> page = this.page(dto.page(), wrapper);
        //查询出该一级菜单对应的二级菜单
        List<SysMenu> list = page.getRecords();
        List<SysMenu> menus = list.stream().map(item -> {
            //有子菜单
            Long menuId = item.getMenuId();
            //根据menuId查询出所有的二级菜单
            QueryWrapper<SysMenu> wrapper1= new QueryWrapper<>();
            wrapper1.eq("parent_id",menuId)
                    .orderByAsc("order_num");
            List<SysMenu> subMenus = this.baseMapper.selectList(wrapper1);
            item.setChildren(subMenus);
            return item;
        }).collect(Collectors.toList());
        page.setRecords(menus);
        return new PageUtils(page);
    }

    @Override
    public List<SysMenu> listParent() {
        List<SysMenu> list = this.baseMapper.selectList(new QueryWrapper<SysMenu>().eq("parent_id", 0));
        return list;
    }

    @SystemLog("菜单的添加/更新")
    @Override
    public void saveOrUpdateMenu(SysMenu menu) {

        if (menu.getMenuId()>0){
            //表示是更新操作
            this.updateById(menu);
        }
        else {
            if (menu.getParentId()==null){
                menu.setParentId(0l);
            }
            //新增操作
            this.save(menu);
        }
    }

    @Override
    public SysMenu queryMenuById(Long menuId) {
        return this.baseMapper.selectById(menuId);
    }
}
