package com.xphsc.auth.api.controller;

import com.xphsc.auth.api.common.response.PageResult;
import com.xphsc.auth.api.model.request.ListUserQuery;
import com.xphsc.auth.api.model.response.UserDTO;
import com.xphsc.auth.api.service.SysService;
import com.xphsc.easyjdbc.page.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author huipei.x
 * @data 创建时间 2018/8/21
 * @description 类说明 :
 */
@RestController
@RequestMapping(value = "/api/user")
public class UserController {
   @Autowired
    private SysService service;

    @PostMapping(value = "/query")
    public PageResult listUser(ListUserQuery listUserQuery) {
        PageInfo<UserDTO> pageInfo=service.listUser(listUserQuery);
        return  new PageResult().ok(
                pageInfo.getTotal(),
                pageInfo.getList()
        );
    }
}
