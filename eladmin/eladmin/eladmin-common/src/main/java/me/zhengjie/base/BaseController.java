package me.zhengjie.base;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

public class BaseController {

    private Map<Integer,String>errorMap=new HashMap<>();

    @PostConstruct
    private void init(){
        ErrorMsg[] values = ErrorMsg.values();
        for (ErrorMsg errorMsg:values){
            String name = errorMsg.name();
            String[] e_s = name.split("E_");
            Integer errCode=Integer.parseInt(e_s[1]);
            errorMap.put(errCode,errorMsg.getMsg());
        }
    }
    /**
     * 请求成功
     * @param data 数据体
     * @param <T>
     * @return
     */
    protected <T> ResponseEntity<T> success(T data){
        return new ResponseEntity<>(0,"请求成功",data);
    }

    /**
     * 业务逻辑错误
     * @param code
     * @param msg
     * @return
     */
    protected ResponseEntity error(int code, String msg){
        return new ResponseEntity(code,msg,null);
    }

    /**
     * 业务逻辑错误，根据错误码返回错误信息
     * @param code
     * @return
     */
    protected ResponseEntity error(int code){
        return new ResponseEntity(code,errorMap.get(code),null);
    }

    /**
     * 业务逻辑错误，根据错误码返回错误信息
     * @param errorMsg
     * @return
     */
    protected ResponseEntity error(ErrorMsg errorMsg){
        String name = errorMsg.name();
        int code = Integer.parseInt(name.split("_")[1]);
        return new ResponseEntity(code,errorMap.get(code),null);
    }
}
