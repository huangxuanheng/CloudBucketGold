package me.zhengjie.utils;

/**
 * 模块类型
 */

public enum ModuleType {
    ALL(10,"公共定义"),
    USER(12,"用户模块"),
    CONSUMER(20,"消费商模块"),
    ;
    private int code;
    private String msg;
    ModuleType(int code,String msg){
        this.code=code;
        this.msg=msg;
    }

    public int getCode() {
        return code;
    }
}
