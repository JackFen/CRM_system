package com.ws.sys.controller;

import com.ws.common.constant.SystemConstant;
import com.ws.common.execption.BizException;
import com.ws.common.result.IgnoredResultWrapper;
import com.ws.common.result.ResultWrapper;
import com.ws.common.util.PageUtils;
import com.ws.sys.entity.SysUser;
import com.ws.sys.model.SysUserQueryDTO;
import com.ws.sys.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

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

    //测试swagger用，因为配置了springSecurity,所以不能直接访问swagger的图形化页面，需要使用token才能访问
    @ApiOperation(value = "查询系统用户-wrapper",notes = "查询用户-wrapper")
    @GetMapping("/list0")
    public List<SysUser> list0(){
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
    //end-测试swagger

    //正式的业务代码
    @ApiOperation(value = "查询系统用户",notes = "查询用户")
    @GetMapping("/list")
    public PageUtils list(SysUserQueryDTO dto){
        return userService.queryPage(dto);
    }
    @ApiOperation(value = "检查用户名称是否重复",notes = "校验用户名称")
    @GetMapping("/checkUserName")
    public String checkUserName(String username){
        //flag等于true 账号存在，反之不存在
        boolean flag=userService.checkUserName(username);
        return flag? SystemConstant.CHECK_SUCCESS:SystemConstant.CHECK_FAIL;
    }
    @ApiOperation(value = "添加账号",notes = "添加账号")
    @PostMapping("/save")
    public String save(@RequestBody SysUser sysUser){
        userService.saveOrUpdateUser(sysUser);
        return "success";
    }
    @ApiOperation(value = "根据Id查询",notes = "根据Id查询用户信息")
    @GetMapping("/queryUserById")
    public SysUser queryUserById(Long userId){
        return userService.queryByUserId(userId);
    }
}
