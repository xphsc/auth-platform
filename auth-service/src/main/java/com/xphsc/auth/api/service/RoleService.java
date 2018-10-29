package com.xphsc.auth.api.service;

import com.xphsc.auth.api.model.response.RoleDTO;
import com.xphsc.easyjdbc.page.PageInfo;


import java.util.Collection;
import java.util.Map;

/**
 * @author huipei.x
 * @date 创建时间 2018-8-10
 * @description 类说明 :
 */
public interface RoleService {
    public PageInfo<RoleDTO> listRole(Integer pageNum, Integer pageSize);
    public Map<String, Collection<String>> getMetaSource();
}
