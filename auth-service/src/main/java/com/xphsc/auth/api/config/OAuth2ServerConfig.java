package com.xphsc.auth.api.config;

import com.xphsc.auth.api.common.exception.CustomOAuth2Exception;
import com.xphsc.auth.api.common.handler.CustomAccessExceptionDeniedHandler;
import com.xphsc.auth.api.common.handler.OAuth2ExceptionEntryPoint;
import com.xphsc.auth.api.service.security.ClientDetailsServiceImpl;
import com.xphsc.auth.api.service.security.SecurityAccessDecisionManager;
import com.xphsc.auth.api.service.security.SecurityMetadataSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

/**
 * @author huipei.x
 * @data 创建时间 2018/8/21
 * @description 类说明 :
 */
@Configuration
public class OAuth2ServerConfig {

    private static final String RESOURCE_ID = "restApi";

    @EnableResourceServer
    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) {
            resources.resourceId(RESOURCE_ID);
            resources.authenticationEntryPoint(authenticationEntryPoint());
            CustomAccessExceptionDeniedHandler customAccessDeniedHandler=new CustomAccessExceptionDeniedHandler();
            resources.accessDeniedHandler(customAccessDeniedHandler);
        }


        @Bean
        public SecurityAccessDecisionManager accessDecisionManager() {
            return new SecurityAccessDecisionManager();
        }
        @Bean
        public SecurityMetadataSourceService securityMetadataSource() {
            SecurityMetadataSourceService metaSource = new SecurityMetadataSourceService();
            metaSource.setAnonymousUrls(
                    "/userInfo/**",
                    "/oauth/token/revoke",
                    "/hystrix*/**",
                    "/*/doc.html",
                    "/*/webjars/**",
                    "/*/swagger-resources/**",
                    "/*/v2/api-docs/**"
            );
            return metaSource;
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            // @formatter:off
            http.requestMatchers().antMatchers("/**")
                    .and()
                    .authorizeRequests()
                    .anyRequest().authenticated()
                    .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                        @Override
                        public <O extends FilterSecurityInterceptor> O postProcess(O fsi) {
                            fsi.setAccessDecisionManager(accessDecisionManager());
                            fsi.setSecurityMetadataSource(securityMetadataSource());
                            return fsi;
                        }
                    });
            // @formatter:on
        }
    }

    @Configuration
    @EnableAuthorizationServer
    protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

        @Autowired
        @Qualifier("authenticationManagerBean")
        private AuthenticationManager authenticationManager;

        @Autowired
        RedisConnectionFactory redisConnectionFactory;

        @Bean
        public TokenStore tokenStore() {
            return new RedisTokenStore(redisConnectionFactory);
        }

        @Bean
        public ClientDetailsService clientDetailsService() {
            return new ClientDetailsServiceImpl();
        }


        @Autowired
        private UserDetailsService userDetailsService;

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients.withClientDetails(clientDetailsService());
        }




        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
            endpoints.authenticationManager(authenticationManager);
            endpoints.tokenStore(new RedisTokenStore(redisConnectionFactory));
            endpoints.userDetailsService(userDetailsService);
            endpoints.exceptionTranslator(webResponseExceptionTranslator());// 自定义认证异常信息
            endpoints.allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
        }
        @Override
        public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
            //允许表单认证
            oauthServer.allowFormAuthenticationForClients();
        }
    }

    public static OAuth2ExceptionEntryPoint authenticationEntryPoint(){
        OAuth2ExceptionEntryPoint entryPoint = new OAuth2ExceptionEntryPoint();
        entryPoint.setExceptionTranslator(webResponseExceptionTranslator());
        return entryPoint;
    }

    public static WebResponseExceptionTranslator webResponseExceptionTranslator() {
        return new DefaultWebResponseExceptionTranslator() {
            @Override
            public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
                ResponseEntity<OAuth2Exception> responseEntity = super.translate(e);
                OAuth2Exception body = responseEntity.getBody();
                HttpHeaders headers = new HttpHeaders();
                headers.setAll(responseEntity.getHeaders().toSingleValueMap());
                CustomOAuth2Exception ex = new CustomOAuth2Exception(body);
                return new ResponseEntity(ex, headers, responseEntity.getStatusCode());
            }
        };
    }

}
