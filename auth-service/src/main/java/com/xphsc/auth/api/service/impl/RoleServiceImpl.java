package com.xphsc.auth.api.service.impl;

import com.github.xtool.collect.Lists;
import com.github.xtool.collect.Maps;
import com.github.xtool.mutable.Integers;
import com.github.xtool.util.StringUtil;
import com.xphsc.auth.api.dao.SysRoleDao;
import com.xphsc.auth.api.model.SysPrivilege;
import com.xphsc.auth.api.model.SysRole;
import com.xphsc.auth.api.model.response.RoleDTO;
import com.xphsc.auth.api.service.RoleService;
import com.xphsc.easyjdbc.EasyJdbcSelector;
import com.xphsc.easyjdbc.core.entity.Example;
import com.xphsc.easyjdbc.page.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author huipei.x
 * @date 创建时间 2018-8-10
 * @description 类说明 :
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private SysRoleDao sysRoleDao;


    @Override
    public PageInfo<RoleDTO> listRole(Integer pageNum, Integer pageSize) {
        Example example= sysRoleDao.example().
                entityClass(RoleDTO.class);
        if(Integers.isNotEmpty(pageNum)){
            example.pageInfo(pageNum,pageSize);
        }
        return example.page();
    }

    @Override
    public Map<String, Collection<String>> getMetaSource() {
        Map<String, Collection<String>> resourceMap = Maps.newHashMap();
        List<SysRole> roles = sysRoleDao.findAll();

        for (SysRole role : roles) {
            String roleName = role.getName();
            List<SysPrivilege> resources = getPrivileges(role.getId(), 1);
            for (SysPrivilege privilege : resources) {
                String url = privilege.getUrl();
                if(StringUtil.isEmpty(url)) {continue;	}
                if (resourceMap.containsKey(url)) {
                    Collection<String> value = resourceMap.get(url);
                    value.add(roleName);
                    resourceMap.put(url, value);
                } else {
                    Collection<String> value = Lists.newLinkedList();
                    value.add(roleName);
                    resourceMap.put(url, value);
                }
            }
        }

        return resourceMap;
    }



    public List<SysPrivilege> getPrivileges(Integer id, Integer type){
        EasyJdbcSelector select=sysRoleDao.selector();
        select.SELECT("*").
                FROM("sys_privilege").
                WHERE("id in(select privilege_id from sys_role_privilege where role_id=?)").WHERE("enabled=1")
                .parameter(id);
        select.entityClass(SysPrivilege.class);

        return  select.list();
    }
}
