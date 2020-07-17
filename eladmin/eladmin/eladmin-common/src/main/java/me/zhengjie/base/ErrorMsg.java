package me.zhengjie.base;

public enum ErrorMsg {
    /**
     * 没有权限
     */
    E_40000("未登录不能操作"),
    E_40001("当前操作没有权限"),


    E_50001("用户名或者密码为空"),
    E_50002("用户密码错误"),
    E_50003("无法查询到该用户，请检查用户名是否正确"),
    E_50004("用户名或者密码错误"),

    E_20000("分页查询没有指定查询匹配属性"),




    E_60000("没有查询到数据"),
    E_60001("数据已经存在"),
    ;

    private String msg;
    ErrorMsg(String msg){
        this.msg=msg;
    }

    public String getMsg() {
        return msg;
    }

    public int getCode(){
        String code = name().substring(name().indexOf("_") + 1);
        return Integer.parseInt(code);
    }

    public static ErrorMsg getInstance(int code){
        return ErrorMsg.valueOf("E_"+code);
    }
}
