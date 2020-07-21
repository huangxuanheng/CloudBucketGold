package me.zhengjie.utils;

/**
 * 错误码统一定义，长度不能超过3位数，错误码可以重复
 */
public enum ErrorMsg {
    SUCCESS(100,"请求成功",ModuleType.ALL),
    /**
     * 没有权限
     */
    NO_POMISSTION(200,"没有数据权限",ModuleType.CONSUMER),
    ERR_ARR_PARAM_LEN(400,"数组参数不能为0",ModuleType.CONSUMER),
    ERR_PARAM(401,"参数不能为空",ModuleType.CONSUMER),
    PARENT_DONT_SELF(501,"上级消费商不能是自己",ModuleType.CONSUMER),
    HAS_SUB_CONSUMER(502,"当前消费商有子消费商，不能删除",ModuleType.CONSUMER),
    SUB_CONSUMER_HAS_SELF(503,"上级消费商已经包含当前用户",ModuleType.CONSUMER),
    UP_LINE_DONT_DOWN(504,"指定的上级已经存在于当前消费商的下级",ModuleType.CONSUMER),
    NO_DEFINE_DATA_TRANFORM(800,"service实现类必须要指定注解@DataTransform标识数据实体转换类型",ModuleType.CONSUMER),
    ;

    private int code;
    private String msg;
    private ModuleType type;
    ErrorMsg(int code, String msg, ModuleType type){
        this.code=code;
        this.msg=msg;
        this.type=type;
    }

    public String getMsg() {
        return msg;
    }


    /**
     * 具备更细粒度的错误码，模块错误码+实际业务错误码
     * @return
     */
    public int getRealCode() {
        return Integer.parseInt(type.getCode()+""+code);
    }

    public static ErrorMsg getInstance(int code){
        return ErrorMsg.valueOf("E_"+code);
    }
}
