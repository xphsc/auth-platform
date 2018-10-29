package com.xphsc.auth.api.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author huipei.x
 * @DATE 创建时间 2018/8/6
 * @description 类说明 :
 */
@Data
@Entity
@Table(name = "oauth_client_details")
public class OauthClientDetails {
   @Id
   @Column(name="client_id")
    private String clientId;
    @Column(name="client_secret")
    private String clientSecret;
    @Column(name="resource_ids")
    private String resourceIds;
    @Column(name="scope")
    private String scope;
    @Column(name="authorized_grant_types")
    private String authorizedGrantTypes;
    @Column(name="web_server_redirect_uri")
    private String webServerRedirectUri;
    @Column(name="authorities")
    private String authorities;
    @Column(name="access_token_validity")
    private Integer accessTokenValidity;
    @Column(name="refresh_token_validity")
    private Integer refreshTokenValidity;
    @Column(name="additional_information")
    private String additionalInformation;
    @Column(name="auto_approve")
    private String autoApprove;
    @Column(name="user_name")
    private String userName;


}
