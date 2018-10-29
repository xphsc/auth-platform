package com.xphsc.auth.api.model.security;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * @author huipei.x
 * @date 创建时间 2018-8-10
 * @description 类说明 :
 */
@Data
public class SecUser extends User {

    private static final long serialVersionUID = 1L;

    private Integer userId;
    private String fullName;
    private String sessionId;
    private String srcIp;
    private String destIp;
    private String roleNames;



    public SecUser(Integer userId, String username, String password, String fullName, boolean enabled, boolean accountNonExpired,
                   boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.userId = userId;
        this.fullName = fullName;
    }
}
