package com.ws.sys.controller;

import com.ws.common.execption.BizException;
import com.ws.common.result.IgnoredResultWrapper;
import com.ws.common.result.ResultWrapper;
import com.ws.sys.entity.SysUser;
import com.ws.sys.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 系统用户 前端控制器
 * </p>
 *
 * @author jack
 * @since 2023-01-09
 */
@Api(tags="系统用户",value = "SysUser")
@CrossOrigin //放开跨越请求
@RestController
@RequestMapping("/sys/sysUser")
public class SysUserController {
    @Autowired
    private ISysUserService userService;

    @ApiOperation(value = "查询系统用户-wrapper",notes = "查询用户-wrapper")
    @GetMapping("/list")
    public List<SysUser> list(){
        return userService.list();
    }

    @IgnoredResultWrapper
    @ApiOperation(value = "查询系统用户-noWrapper",notes = "查询用户-noWrapper")
    @GetMapping("/list1")
    public List<SysUser> list1(){
        return userService.list();
    }

    @ApiOperation(value = "查询系统用户-自定义ResultWrapper",notes = "查询用户-自定义ResultWrapper")
    @GetMapping("/list2")
    public ResultWrapper list2(){
        ResultWrapper wrapper=new ResultWrapper<>();
        wrapper.setCode("200");
        wrapper.setData(userService.list());
        wrapper.setMessage("自定义ResultWrapper");
        return wrapper;
    }
    @ApiOperation(value = "查询系统用户-异常",notes = "查询用户-异常")
    @GetMapping("/list3")
    public ResultWrapper list3(){
        ResultWrapper wrapper=new ResultWrapper<>();
        wrapper.setCode("200");
        wrapper.setData(userService.list());
        wrapper.setMessage("自定义ResultWrapper");
        if (true){
            throw new BizException("500","业务出错...");
        }
        return wrapper;
    }
}
