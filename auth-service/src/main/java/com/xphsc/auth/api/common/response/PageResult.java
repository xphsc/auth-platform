package com.xphsc.auth.api.common.response;

import com.xphsc.auth.api.common.enums.ResultEnum;

import java.util.List;

/**
 * @author huipei.x
 * @date 创建时间 2018-8-10
 * @description 类说明 :
 */
public class PageResult<T> {
    private int code;

    private String msg;

    private long total;

    private List<T> data;

    public PageResult() {
    }

    public PageResult ok(long total, List<T> rows){
        this.data = rows;
        this.total = total;
        this.code = ResultEnum.OK.getCode();
        this.msg = ResultEnum.OK.getMsg();
        return this;
    }

    public PageResult ok(List<T> rows){
        this.data = rows;
        this.total = rows.size();
        this.code = ResultEnum.OK.getCode();
        this.msg = ResultEnum.OK.getMsg();
        return this;
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
        this.total = data.size();
    }


}
