package com.ws.sys.controller;

import com.ws.common.constant.SystemConstant;
import com.ws.common.util.PageUtils;
import com.ws.sys.entity.SysMenu;
import com.ws.sys.model.MenuUpdateDTO;
import com.ws.sys.model.SysMenuQueryDTO;
import com.ws.sys.service.ISysMenuService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 * 菜单管理 前端控制器
 * </p>
 *
 * @author jack
 * @since 2023-01-17
 */
@Controller
@RequestMapping("/sys/sysMenu")
public class SysMenuController {
    @Autowired
    private ISysMenuService menuService;
    @ApiOperation(value = "查询菜单信息",notes = "查询菜单信息")
    @GetMapping("/list")
    public PageUtils list(SysMenuQueryDTO dto){
        return menuService.listPage(dto);
    }

    @ApiOperation(value = "查询父菜单信息",notes = "查询父菜单信息")
    @GetMapping("/listParent")
    public List<SysMenu> listParent(){
        return menuService.listParent();
    }

    @ApiOperation(value = "操作菜单数据",notes = "添加/更新菜单数据")
    @PostMapping("/save")
    public String save(@RequestBody SysMenu menu){
        if (menu!=null){
            menuService.saveOrUpdateMenu(menu);
        }
        return SystemConstant.CHECK_SUCCESS;
    }
    @ApiOperation(value = "根据Id查询菜单",notes = "根据Id查询菜单")
    @GetMapping("/queryMenuById")
    public MenuUpdateDTO queryMenuById(Long menuId){
        SysMenu sysMenu = menuService.queryMenuById(menuId);
        List<SysMenu> parents = menuService.listParent();
        return new MenuUpdateDTO(parents,sysMenu);
    }
}
