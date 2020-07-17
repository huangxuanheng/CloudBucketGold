package me.zhengjie.base;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseEntity<T> {
    /**
     * 错误码
     */
    private int code;
    /**
     * 错误信息说明
     */
    private String msg;
    /**
     * 数据体
     */
    private T data;

    public ResponseEntity(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
