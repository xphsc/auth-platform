package com.xphsc.auth.api.dao.impl;

import com.xphsc.auth.api.dao.SysRoleDao;
import com.xphsc.auth.api.model.SysRole;
import com.xphsc.easyjdbc.core.SimpleJdbcDao;
import org.springframework.stereotype.Repository;

/**
 * @author huipei.x
 * @date 创建时间 2018-8-10
 * @description 类说明 :
 */
@Repository
public class SysRoleDaoImpl extends SimpleJdbcDao<SysRole> implements SysRoleDao {
}