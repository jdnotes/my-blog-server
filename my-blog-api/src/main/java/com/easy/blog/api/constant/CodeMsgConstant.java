package com.easy.blog.api.constant;

/**
 * @author zhouyong
 * @date 2019/6/8
 */
public class CodeMsgConstant {

    //通用的错误码
    public static CodeMsgConstant SUCCESS = new CodeMsgConstant(200, "success");
    public static CodeMsgConstant SERVER_ERROR = new CodeMsgConstant(500100, "服务端异常");
    public static CodeMsgConstant PARAM_BIND_ERROR = new CodeMsgConstant(500101, "参数校验异常");

    //登录模块 5002XX
    public static CodeMsgConstant SESSION_ERROR = new CodeMsgConstant(500210, "Session不存在或者已经失效");
    public static CodeMsgConstant PASSWORD_EMPTY = new CodeMsgConstant(500211, "登录密码不能为空");
    public static CodeMsgConstant MOBILE_EMPTY = new CodeMsgConstant(500212, "手机号不能为空");
    public static CodeMsgConstant MOBILE_ERROR = new CodeMsgConstant(500213, "手机号格式错误");
    public static CodeMsgConstant MOBILE_NOT_EXIST = new CodeMsgConstant(500214, "手机号不存在");
    public static CodeMsgConstant PASSWORD_ERROR = new CodeMsgConstant(500215, "密码错误");

    //商品模块 5003XX

    //订单模块 5004XX

    //秒杀模块 5005XX

    private int code;
    private String msg;

    private CodeMsgConstant() {
    }

    private CodeMsgConstant(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "CodeMsgConstant [code=" + code + ", msg=" + msg + "]";
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
}