package com.ws.sys.model;

import com.ws.common.model.PageDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SysUserQueryDTO extends PageDTO {
    @ApiModelProperty("用户名称")
    private String username;
}
