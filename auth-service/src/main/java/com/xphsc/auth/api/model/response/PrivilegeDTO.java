package com.xphsc.auth.api.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author huipei.x
 * @date 创建时间 2018-8-10
 * @description 类说明 :
 */
@Data
public class PrivilegeDTO {
    @ApiModelProperty( value= "资源权限ID")
    private Integer id;
    @ApiModelProperty( value= "code")
    private String code;
    @ApiModelProperty( value= "资源权限名称")
    private String name;
    @ApiModelProperty( value= "资源权限URL")
    private String url;
    private Boolean enabled;
    private String ico;
    private Integer type;
    private Integer parentId;
    private Date createTime;
    private Date updateTime;

}
