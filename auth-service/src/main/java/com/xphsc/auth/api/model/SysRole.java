package com.xphsc.auth.api.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author huipei.x
 * @date 创建时间 2018-8-10
 * @description 类说明 :
 */
@Data
@Entity
@Table(name = "sys_role")
public class SysRole {
    @Id
    private Integer id;
    private String name;
    private String description;
    private String chineseName;
}
