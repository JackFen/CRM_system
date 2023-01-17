package com.ws.sys.model;

import com.ws.sys.entity.SysMenu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 更新数据需要的DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuUpdateDTO {
    private List<SysMenu> parents;
    private SysMenu menu;
}
