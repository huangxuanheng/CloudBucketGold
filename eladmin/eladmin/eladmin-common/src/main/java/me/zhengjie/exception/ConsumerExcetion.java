package me.zhengjie.exception;

import lombok.Getter;
import me.zhengjie.utils.ErrorMsg;
import me.zhengjie.utils.SpringContextHolder;

/**
 * 处理所有消费商异常
 */
@Getter
public class ConsumerExcetion extends RuntimeException{
    private int code;

    public ConsumerExcetion(int code,String msg){
        super(msg);
        if((code+"").length()<5){
            throw new BadRequestException("错误码定义不符合规范，请参考枚举类#ErrorMsg定义");
        }
        String serverErrCode = SpringContextHolder.getProperty("consumer.server.err.code");
        this.code=Integer.parseInt(serverErrCode+code);
    }

    public ConsumerExcetion(ErrorMsg errorMsg){
        this(errorMsg.getRealCode(),errorMsg.getMsg());
    }
}
