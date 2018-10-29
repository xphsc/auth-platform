package com.xphsc.auth.api.dao;

import com.xphsc.auth.api.model.OauthClientDetails;
import com.xphsc.easyjdbc.core.EasyJdbcDao;
import org.springframework.stereotype.Repository;


/**
 * @author huipei.x
 * @date 创建时间 2018-8-10
 * @description 类说明 :
 */
@Repository
public interface OauthClientDetailsDao extends EasyJdbcDao<OauthClientDetails> {
}
