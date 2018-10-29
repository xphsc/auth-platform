package com.xphsc.auth.api.service.impl;

import com.xphsc.auth.api.dao.OauthClientDetailsDao;
import com.xphsc.auth.api.model.response.OauthClientDTO;
import com.xphsc.auth.api.service.OauthClientDetailsService;
import com.xphsc.easyjdbc.core.entity.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author huipei.x
 * @date 创建时间 2018-8-10
 * @description 类说明 :
 */
@Service
public class OauthClientDetailsServiceImpl implements OauthClientDetailsService {
    @Autowired
    private OauthClientDetailsDao oauthClientDetailsDao;

    @Override
    public OauthClientDTO getClientByClientId(String clientId) {
        Example example= oauthClientDetailsDao.example();
        example.createCriteria()
                .andEqualTo("clientId",clientId);
        example.entityClass(OauthClientDTO.class);
        OauthClientDTO oauthClientDetails=example.get();
        return oauthClientDetails;
    }
}
