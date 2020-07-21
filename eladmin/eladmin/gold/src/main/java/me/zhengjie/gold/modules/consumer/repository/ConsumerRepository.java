package me.zhengjie.gold.modules.consumer.repository;

import me.zhengjie.base.BaseRepository;
import me.zhengjie.gold.modules.consumer.domain.Consumer;
import me.zhengjie.gold.modules.consumer.domain.Grade;
import me.zhengjie.gold.modules.consumer.service.dto.ConsumerDto;

import java.util.List;

/**
* @author Harry
* @date 2020-07-18
*/
public interface ConsumerRepository extends BaseRepository<Consumer, Long> {
    /**
     * 根据父节点ID查找所有的子消费商
     * @param parentId
     * @return
     */
    List<Consumer> findByParentId(Long parentId);
}