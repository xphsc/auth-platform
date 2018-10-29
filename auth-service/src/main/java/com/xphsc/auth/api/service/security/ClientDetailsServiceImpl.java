package com.xphsc.auth.api.service.security;

import com.github.xtool.collect.Lists;
import com.github.xtool.collect.Sets;
import com.github.xtool.util.StringUtil;
import com.xphsc.auth.api.model.response.OauthClientDTO;
import com.xphsc.auth.api.service.OauthClientDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author huipei.x
 * @DATE 创建时间 2018/8/6
 * @description 类说明 :
 */
@Service
public class ClientDetailsServiceImpl implements ClientDetailsService {

    @Autowired
    private OauthClientDetailsService oauthClientDetailsService;
    @Override
    public ClientDetails loadClientByClientId(String s) throws ClientRegistrationException {
        OauthClientDTO oauthClientDetails=oauthClientDetailsService.getClientByClientId(s);
        BaseClientDetails details = new BaseClientDetails();
        details.setClientId(oauthClientDetails.getClientId());
        List<String> resourceIds= Lists.newLinkedList();
        if(StringUtil.isNotEmpty(oauthClientDetails.getResourceIds())){
            String [] resourceIdArray=oauthClientDetails.getResourceIds().split(",");
            for (String  resourceId : resourceIdArray) {
                resourceIds.add( resourceId);
            }
        }

        details.setResourceIds(resourceIds);
        List<String> scopes=Lists.newLinkedList();
        if(StringUtil.isNotEmpty(oauthClientDetails.getScope())){
            String [] scopeArray=oauthClientDetails.getScope().split(",");
            for (String  scopeId : scopeArray) {
                scopes.add( scopeId);
            }
        }
        details.setScope(scopes);
        List<String> authorizedGrantTypes=Lists.newLinkedList();
        if(StringUtil.isNotEmpty(oauthClientDetails.getAuthorizedGrantTypes())){
            String [] authorizedGrantTypesArray=oauthClientDetails.getAuthorizedGrantTypes().split(",");
            for (String authorizedGrantType : authorizedGrantTypesArray) {
                authorizedGrantTypes.add(authorizedGrantType);
            }
        }
        details.setAuthorizedGrantTypes(authorizedGrantTypes);
        Collection<GrantedAuthority> auths = Lists.newLinkedList();
        if(StringUtil.isNotEmpty(oauthClientDetails.getAuthorities())){
            String [] authArray=oauthClientDetails.getAuthorities().split(",");
            for (String auth : authArray) {
                auths.add(new SimpleGrantedAuthority(auth));
            }
        }
        details.setAuthorities(auths);
        Set<String> registeredRedirectUris= Sets.newConcurrentHashSet();
        registeredRedirectUris.add(oauthClientDetails.getWebServerRedirectUri());
        details.setRegisteredRedirectUri(registeredRedirectUris);
        if(oauthClientDetails.getAccessTokenValidity()!=null){
            details.setAccessTokenValiditySeconds(oauthClientDetails.getAccessTokenValidity());
        } if(oauthClientDetails.getRefreshTokenValidity()!=null){
            details.setRefreshTokenValiditySeconds(oauthClientDetails.getRefreshTokenValidity());
        }

        details.setClientSecret(oauthClientDetails.getClientSecret());
        return details;
    }
}
