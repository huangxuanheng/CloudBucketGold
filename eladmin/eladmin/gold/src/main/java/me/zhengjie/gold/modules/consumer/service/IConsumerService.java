package me.zhengjie.gold.modules.consumer.service;

import me.zhengjie.base.IBaseService;
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

    List<ConsumerDto> getChildrensByQueryCode(String queryCode);
}
