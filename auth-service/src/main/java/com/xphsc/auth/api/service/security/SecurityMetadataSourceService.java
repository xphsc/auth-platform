package com.xphsc.auth.api.service.security;

import com.github.xtool.collect.Maps;
import com.google.common.collect.Lists;
import com.xphsc.auth.api.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @author huipei.x
 * @data 创建时间 2018/8/21
 * @description 类说明 :
 */
@Component(" securityMetadataSourceService")
public class SecurityMetadataSourceService  implements FilterInvocationSecurityMetadataSource {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private static Map<String, Collection<ConfigAttribute>> resourceMap = new ConcurrentSkipListMap<String, Collection<ConfigAttribute>>();
    private static Map<String, Collection<ConfigAttribute>> anonymousMap =Maps.newHashMap();

    @Autowired
    private RoleService roleService;


    public SecurityMetadataSourceService setAnonymousUrls(String... urls){
        if(urls != null && urls.length > 0)	{
            Collection<ConfigAttribute> anonymousAtts =Lists.newLinkedList();
            ConfigAttribute anonymousCa = new SecurityConfig("ROLE_ANONYMOUS");
            anonymousAtts.add(anonymousCa);

            for(String url : urls){
                anonymousMap.put(url, anonymousAtts);
            }
        }

        return this;
    }

      @PostConstruct
    public void loadMetadataSource() {
        resourceMap.clear();
        resourceMap.putAll(anonymousMap);
        Map<String, Collection<String>> map = roleService.getMetaSource();
        resourceMap.putAll(convertMap(map));
        logger.info("loadMetadataSource finish.");
    }

    // 根据URL，找到相关的权限配置。
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        if(resourceMap.size() == 0){
            loadMetadataSource();
        }

        FilterInvocation filterInvocation = (FilterInvocation) object;

        for(String url : anonymousMap.keySet()) {	// 匿名访问权限
            RequestMatcher requestMatcher = new AntPathRequestMatcher(url);
            HttpServletRequest httpRequest = filterInvocation.getHttpRequest();

            if (requestMatcher.matches(httpRequest)) {
                return anonymousMap.get(url);
            }
        }
        for(String url : resourceMap.keySet()) {	// 授权访问权限
            RequestMatcher requestMatcher = new AntPathRequestMatcher(url);
            HttpServletRequest httpRequest = filterInvocation.getHttpRequest();

            if (requestMatcher.matches(httpRequest)) {
                return resourceMap.get(url);
            }
        }
       Collection<ConfigAttribute> denyAll = Lists.newArrayList();
        ConfigAttribute anonymousCa =  new SecurityConfig("ROLE_DENYALL");
        denyAll.add(anonymousCa);
     return denyAll;
    }



    private Map<String, Collection<ConfigAttribute>> convertMap(Map<String,Collection<String>> map){
        Map<String, Collection<ConfigAttribute>> resultMap = Maps.newHashMap();
        for(String url : map.keySet()){
            Collection<String> roles = map.get(url);

            if(roles != null && roles.size() > 0) {
                Collection<ConfigAttribute> cas = new ArrayList<ConfigAttribute>();
                for(String role : roles){
                    ConfigAttribute ca = new SecurityConfig(role);
                    cas.add(ca);
                }
                resultMap.put(url, cas);
            }
        }

        return resultMap;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }

}

