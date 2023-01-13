package com.ws.sys.service;

import com.ws.common.util.PageUtils;
import com.ws.sys.entity.SysLog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ws.sys.model.SysLogQueryDTO;

/**
 * <p>
 * 系统日志 服务类
 * </p>
 *
 * @author jack
 * @since 2023-01-13
 */
public interface ISysLogService extends IService<SysLog> {

    PageUtils listPage(SysLogQueryDTO dto);
}
