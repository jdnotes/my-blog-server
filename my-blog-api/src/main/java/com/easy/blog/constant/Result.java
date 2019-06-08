package com.easy.blog.constant;

import java.io.Serializable;

/**
 * @author zhouyong
 * @date 2019/6/8
 */
public class Result<T> implements Serializable {

    private int code;
    private String msg;
    private T data;

    /**
     * 成功时候的调用
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(data);
    }

    /**
     * 失败时候的调用
     */
    public static <T> Result<T> error() {
        return new Result<>(CodeMsgConstant.SERVER_ERROR);
    }

    /**
     * 失败时候的调用
     */
    public static <T> Result<T> error(CodeMsgConstant codeMsg) {
        return new Result<>(codeMsg);
    }


    /**
     * 成功的构造函数
     *
     * @param data
     */
    private Result(T data) {
        this.code = 200;
        this.msg = "success";
        this.data = data;
    }

    private Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 失败的构造函数
     *
     * @param codeMsg
     */
    private Result(CodeMsgConstant codeMsg) {
        if (codeMsg != null) {
            this.code = codeMsg.getCode();
            this.msg = codeMsg.getMsg();
        }
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}