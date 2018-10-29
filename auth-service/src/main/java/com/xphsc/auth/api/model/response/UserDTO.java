package com.xphsc.auth.api.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author huipei.x
 * @date 创建时间 2018-8-10
 * @description 类说明 :
 */
@Data
public class UserDTO {
    @ApiModelProperty( value= "用户ID")
    private Integer id;
    @ApiModelProperty( value= "账号")
    protected String username;
    @ApiModelProperty( value= "密码")
    protected String password;
    @ApiModelProperty( value= "用户名称")
    protected String fullName;
    @ApiModelProperty( value= "说明")
    protected String description;
    //拓展字段
    @ApiModelProperty( value= "电子邮件")
    protected String email;
    @ApiModelProperty( value= "手机")
    protected String mobile;
    protected Boolean enabled = Boolean.TRUE;

}
