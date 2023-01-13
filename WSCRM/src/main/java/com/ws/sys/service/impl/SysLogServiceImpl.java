package com.ws.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ws.common.util.PageUtils;
import com.ws.sys.entity.SysLog;
import com.ws.sys.mapper.SysLogMapper;
import com.ws.sys.model.SysLogQueryDTO;
import com.ws.sys.service.ISysLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统日志 服务实现类
 * </p>
 *
 * @author jack
 * @since 2023-01-13
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements ISysLogService {

    /**
     * 查询系统日志信息
     * @param dto 用户名或者用户操作
     * @return
     */
    @Override
    public PageUtils listPage(SysLogQueryDTO dto) {
        QueryWrapper<SysLog> wrapper = new QueryWrapper<SysLog>();
        if(StringUtils.isNotEmpty(dto.getMsg())){
            wrapper.and(item->{
                item.like("username",dto.getMsg())
                        .or().like("operation",dto.getMsg());
            });
        }
        Page<SysLog> page = this.page(dto.page(), wrapper);
        return new PageUtils(page);
    }
}
