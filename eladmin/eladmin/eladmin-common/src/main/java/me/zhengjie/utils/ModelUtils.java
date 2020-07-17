package me.zhengjie.utils;

import me.zhengjie.base.BaseDTO;
import me.zhengjie.base.BaseEntity;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * author harry
 * dept: cloudpivot
 * date : 2020/3/31
 * des:
 */
public class ModelUtils {

    /**
     * 将实体对象转换为model
     * @param entity
     * @param baseClazz
     * @param <T>
     * @return
     */
    public static <T>T toModel(BaseEntity entity, Class<T>baseClazz){
        T model = null;
        try {
            model = baseClazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        BeanUtils.copyProperties(entity,model);
        return model;
    }


    /**
     * 将model转换为entity
     * @param model
     * @param baseClazz
     * @param <T>
     * @return
     */
    public static <T>T toEntity(BaseDTO model, Class<T>baseClazz){
        T entity = null;
        try {
            entity = baseClazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        BeanUtils.copyProperties(model,entity);
        return entity;
    }


    /**
     * 将实体对象转换为model
     * @param entitys
     * @param baseClazz
     * @param <T>
     * @return
     */
    public static <T> List<T> toModels(List<? extends BaseEntity> entitys, Class<T>baseClazz){
        List<T>models=new ArrayList<>();
        entitys.forEach(e->models.add(toModel(e,baseClazz)));
        return models;
    }
}
