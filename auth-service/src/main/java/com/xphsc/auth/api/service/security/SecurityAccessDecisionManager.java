package com.xphsc.auth.api.service.security;

import com.github.xtool.util.StringUtil;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author huipei.x
 * @data 创建时间 2018/8/21
 * @description 类说明 :
 */
@Component
public class SecurityAccessDecisionManager implements AccessDecisionManager {

    @Override
    public void decide(Authentication authentication, Object object,
                       Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {

        if (configAttributes != null) {
            for (ConfigAttribute ca : configAttributes) {
                String needRole = ca.getAttribute();

                if(needRole.toUpperCase().equals("ROLE_ANONYMOUS")){
                    return;}

                for (GrantedAuthority authority : authentication.getAuthorities()) {
                    if (StringUtil.equals(authority.getAuthority(), "ROLE_" + needRole.toUpperCase())) {
                        return;
                    }
                }
            }
        }

        throw new AccessDeniedException("没有权限进行操作！");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }
    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
