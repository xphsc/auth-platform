package com.xphsc.auth.api.config;

import com.alibaba.fastjson.JSONObject;
import com.xphsc.auth.api.common.exception.ApiException;
import com.xphsc.auth.api.common.util.ContextHolderUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Created by ${huipei.x} on 2017-3-15.
 */
@RestControllerAdvice
@Slf4j
public class ExceptionControllerAdvice {

    private final static String MESSAGE="系统繁忙，请稍后再试";


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Object messageNotReadable(HttpMessageNotReadableException exception){
        JSONObject result=new JSONObject();
        result.put("code",String.valueOf(HttpStatus.BAD_REQUEST));
        result.put("message",MESSAGE);
        result.put("reason", exception.getMessage());
        return result;
    }



    @ExceptionHandler(ApiException.class)
    public Object apiAxception(ApiException exception){
        JSONObject result=new JSONObject();
        result.put("code", String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR));
        result.put("msg",MESSAGE);
        result.put("reason", exception.getMessage());
        return result;
    }

    @ExceptionHandler(value = Exception.class)
    public Object defaultErrorHandler(Exception e)  {
        JSONObject result=new JSONObject();

        if (e instanceof org.springframework.web.servlet.NoHandlerFoundException) {
            result.put("code", String.valueOf(HttpStatus.NOT_FOUND));
        } else {
            result.put("code", String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR));
        }
        result.put("msg",MESSAGE);
        result.put("reason",e.getMessage());
        String message = String.format("接口 [%s] 出现异常，异常摘要：%s",
                ContextHolderUtil.getRequest().getRequestURI(),
                e.getMessage());
        log.error(message,e);
        return result;
    }
}
