package com.xphsc.auth.api.service.security;

import com.github.xtool.collect.Sets;
import com.xphsc.auth.api.model.response.RoleDTO;
import com.xphsc.auth.api.model.response.UserDTO;
import com.xphsc.auth.api.model.security.SecUser;
import com.xphsc.auth.api.service.SysService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import java.util.Collection;
import java.util.List;

/**
 * @author huipei.x
 * @date 创建时间 2018-8-10
 * @description 类说明 :
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {
        Logger logger = LoggerFactory.getLogger(getClass());

        @Autowired
        private SysService sysService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        UserDTO user = null;
         user = sysService.getByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户" + username + " 不存在");
        }
        Collection<GrantedAuthority> grantedAuths = obtainGrantedAuthorities(user);

        boolean enabled = user.getEnabled() == null ? true : user.getEnabled();
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
        UserDetails userdetails = new SecUser(user.getId(), user.getUsername(),
                user.getPassword(), user.getFullName(),enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, grantedAuths);

        return userdetails;
    }


    private Collection<GrantedAuthority> obtainGrantedAuthorities(UserDTO user) {
        Collection<GrantedAuthority> authSet = Sets.newHashSet();
      List<RoleDTO> roles = sysService.getUserRoles(user.getId());
        for (RoleDTO role : roles) {
            authSet.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        }
        return authSet;
    }

}
