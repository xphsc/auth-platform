package com.xphsc.auth.api.common.exception;

import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

import java.util.Map;

/**
 * @author huipei.x
 * @data 创建时间 2018/8/21
 * @description 类说明 :
 */
@com.fasterxml.jackson.databind.annotation.JsonSerialize(using = CustomOAuth2ExceptionJackson2Serializer.class)
public class CustomOAuth2Exception extends OAuth2Exception  {
    private OAuth2Exception exception;

    public CustomOAuth2Exception(OAuth2Exception oe) {
        super(oe.getMessage(), oe);
        this.exception = oe;
    }

    public String getOAuth2ErrorCode() {
        return exception.getOAuth2ErrorCode();
    }

    public int getHttpErrorCode() {
        return exception.getHttpErrorCode();
    }

    public Map<String, String> getAdditionalInformation() {
        return exception.getAdditionalInformation();
    }

    public void addAdditionalInformation(String key, String value) {
        exception.addAdditionalInformation(key, value);
    }
}