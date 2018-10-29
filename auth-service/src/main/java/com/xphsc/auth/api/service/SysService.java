package com.xphsc.auth.api.service;

import com.xphsc.auth.api.model.request.ListUserQuery;
import com.xphsc.auth.api.model.response.RoleDTO;
import com.xphsc.auth.api.model.response.UserDTO;
import com.xphsc.easyjdbc.page.PageInfo;


import java.util.List;

/**
 * @author huipei.x
 * @date 创建时间 2018-8-10
 * @description 类说明 :
 */
public interface SysService {


    public UserDTO getByUserName(String userName);
    public List<RoleDTO> getUserRoles(Integer userId);
    public PageInfo<UserDTO> listUser(ListUserQuery listUserQuery);
}
