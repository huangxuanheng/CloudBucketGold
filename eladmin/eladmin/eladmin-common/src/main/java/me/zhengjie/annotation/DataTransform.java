package me.zhengjie.annotation;

import me.zhengjie.base.BaseDTO;
import me.zhengjie.base.BaseEntity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据转换定义，将dto和entity定义
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DataTransform {
    /**
     * dto 字节码
     * @return
     */
    Class<? extends BaseDTO> dto();

    /**
     * entity字节码
     * @return
     */
    Class<? extends BaseEntity> entity();
}
