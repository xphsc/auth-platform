package com.xphsc.auth.api.service.impl;

import com.github.xtool.mutable.Integers;
import com.xphsc.auth.api.dao.SysUserDao;
import com.xphsc.auth.api.model.request.ListUserQuery;
import com.xphsc.auth.api.model.response.RoleDTO;
import com.xphsc.auth.api.model.response.UserDTO;
import com.xphsc.auth.api.service.SysService;
import com.xphsc.easyjdbc.EasyJdbcSelector;
import com.xphsc.easyjdbc.core.entity.Example;
import com.xphsc.easyjdbc.page.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author huipei.x
 * @date 创建时间 2018-8-10
 * @description 类说明 :
 */
@Service
class SysServiceImpl implements SysService {

    @Autowired
    private SysUserDao sysUserDao;

    @Override
    public UserDTO getByUserName(String userName){
       Example example= sysUserDao.example().
               mapping("userName","username").
               entityClass(UserDTO.class);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("userName",userName);
        return example.get();
    }

   @Override
   public List<RoleDTO> getUserRoles(Integer userId){
        EasyJdbcSelector select=sysUserDao.selector();
       select.SELECT("*").
               FROM("sys_role").
               WHERE("id in(select role_id from sys_user_role where user_id=?)").
               parameter(userId);
       select.entityClass(RoleDTO.class);
        return select.list();
    }

    @Override
    public PageInfo<UserDTO> listUser(ListUserQuery listUserQuery) {
        Example example= sysUserDao.example().
                mapping("userName","username").
                entityClass(UserDTO.class);
        if(Integers.isNotEmpty(listUserQuery.getPageNum())){
            example.pageInfo(listUserQuery.getPageNum(),
                    listUserQuery.getPageSize());
        }
        return example.page();
    }

}
