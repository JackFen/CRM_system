package com.ws.sys.controller;

import com.ws.common.util.PageUtils;
import com.ws.sys.entity.SysLog;
import com.ws.sys.model.SysLogQueryDTO;
import com.ws.sys.service.ISysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 * 系统日志 前端控制器
 * </p>
 *
 * @author jack
 * @since 2023-01-13
 */
@Controller
@CrossOrigin
@RequestMapping("/sys/sysLog")
public class SysLogController {

    @Autowired
    private ISysLogService sysLogService;

    @GetMapping("/list")
    public PageUtils list(SysLogQueryDTO dto){
        PageUtils pageUtils=sysLogService.listPage(dto);
        return pageUtils;
    }
}
