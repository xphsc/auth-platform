package com.xphsc.auth.api.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author huipei.x
 * @date 创建时间 2018-8-10
 * @description 类说明 :
 */
@Data
public class RoleDTO {
    @ApiModelProperty( value= "角色ID")
    private Integer id;
    @ApiModelProperty( value= "角色英文名")
    private String name;
    @ApiModelProperty( value= "说明")
    private String description;
    @ApiModelProperty( value= "角色中文名称")
    private String chineseName;
}
