package com.xphsc.auth.api.dao;

import com.xphsc.auth.api.model.SysUser;
import com.xphsc.easyjdbc.core.EasyJdbcDao;
import org.springframework.stereotype.Repository;


/**
 * @author huipei.x
 * @DATE 创建时间 2018/8/6
 * @description 类说明 :
 */
@Repository
public interface SysUserDao extends EasyJdbcDao<SysUser> {
}
