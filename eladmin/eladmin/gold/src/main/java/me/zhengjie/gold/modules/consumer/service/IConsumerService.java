package me.zhengjie.gold.modules.consumer.service;

import me.zhengjie.base.IBaseService;
import me.zhengjie.gold.modules.consumer.domain.Consumer;
import me.zhengjie.gold.modules.consumer.service.dto.ConsumerDto;

import java.util.List;

/**
 * @author Harry
 * @date 2020/7/18
 * @des 描述：
 */
public interface IConsumerService extends IBaseService {
    /**
     * 根据父节点ID查找所有的子消费商
     * @param parentId
     * @return
     */
    List<ConsumerDto> getChildrensByParentId(Long parentId);

    /**
     * 根据queryCODE查询所有子消费商
     * @param queryCode
     * @return
     */
    List<ConsumerDto> getChildrensByQueryCode(String queryCode);

    /**
     * 根据用户id查找
     * @param userId
     * @return
     */
    List<Consumer>findByUserId(Long userId);
}
