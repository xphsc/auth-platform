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
@Table(name = "sys_privilege")
public class SysPrivilege {
    @Id
    private Integer id;
    private String code;
    private String name;
    private String url;
    private Boolean enabled;
    private String ico;
    private Integer type;
    @Column(name="parent_id")
    private Integer parentId;
    @Column(name="create_user")
    private Integer createUser;
    @Column(name="update_user")
    private Integer updateUser;
    @Column(name="create_time")
    private Date createTime;
    @Column(name="update_time")
    private Date updateTime;
}
