package me.zhengjie.base;

import me.zhengjie.exception.BadRequestException;
import me.zhengjie.utils.ErrorMsg;
import me.zhengjie.utils.ModuleType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

public class BaseController {

    private Map<Integer,String>errorMap=new HashMap<>();

    @PostConstruct
    private void init(){
        ErrorMsg[] values = ErrorMsg.values();
        for (ErrorMsg errorMsg:values){
            errorMap.put(errorMsg.getRealCode(),errorMsg.getMsg());
        }
    }
    /**
     * 请求成功
     * @param data 数据体
     * @param <T>
     * @return
     */
    protected <T> ResponseEntity<T> success(T data){
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    /**
     * 业务逻辑错误
     * @param code
     * @param msg
     * @return
     */
    protected ResponseEntity error(int code, String msg, ModuleType type){
        checkBizErrCode(code);
        //Integer.parseInt(type.getCode()+""+code)
        return new ResponseEntity(msg,HttpStatus.MULTI_STATUS);
    }

    /**
     * 业务逻辑错误，根据错误码返回错误信息
     * @param code
     * @return
     */
//    protected ResponseEntity error(int code, ModuleType type){
//        checkBizErrCode(code);
//        int exCode = Integer.parseInt(type.getCode() + "" + code);
//        return new ResponseEntity(exCode,errorMap.get(exCode),null);
//    }

    private void checkBizErrCode(int code){
        if((code+"").length()!=3){
            throw new BadRequestException("错误码定义不符合规范，必须定义为一个3位数的数字，即：100-999");
        }

    }

    /**
     * 业务逻辑错误，根据错误码返回错误信息
     * @param errorMsg
     * @return
     */
//    protected ResponseEntity error(ErrorMsg errorMsg){
//        return new ResponseEntity(errorMsg.getRealCode(),errorMap.get(errorMsg.getRealCode()),null);
//    }
}
