package com.xphsc.auth.api.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author huipei.x
 * @date 创建时间 2018-8-10
 * @description 类说明 :
 */
@Data
@Entity
@Table(name = "sys_user")
public class SysUser {
    @Id
    private Integer id;
    @Column(name="user_name")
    private String userName;
    @Column(name="full_name")
    private String fullName;
    private String password;
    @Column(name="enabled")
    private Integer enabled;
    private String email;
    private String mobile;
    private String description;
    @Column(name="create_user")
    private Integer createUser;
    @Column(name="update_user")
    private Integer updateUser;
    @Column(name="create_time")
    private Date createTime;
    @Column(name="update_time")
    private Date updateTime;

}
