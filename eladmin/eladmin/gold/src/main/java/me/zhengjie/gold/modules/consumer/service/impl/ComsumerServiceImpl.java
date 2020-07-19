package me.zhengjie.gold.modules.consumer.service.impl;

import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.DataTransform;
import me.zhengjie.base.BaseRepository;
import me.zhengjie.gold.modules.consumer.domain.Consumer;
import me.zhengjie.gold.modules.consumer.repository.ConsumerRepository;
import me.zhengjie.gold.modules.consumer.service.IConsumerService;
import me.zhengjie.gold.modules.consumer.service.dto.ConsumerDto;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Harry
 * @date 2020/7/18
 * @des 描述：
 */

@Service
@RequiredArgsConstructor
@DataTransform(dto = ConsumerDto.class,entity = Consumer.class)
public class ComsumerServiceImpl implements IConsumerService {
    private final ConsumerRepository consumerRepository;
    @Override
    public BaseRepository getJpaRepository() {
        return consumerRepository;
    }

    @Override
    public List<ConsumerDto> getChildrensByParentId(Long parentId) {
        return consumerRepository.findByParentId(parentId);
    }
}