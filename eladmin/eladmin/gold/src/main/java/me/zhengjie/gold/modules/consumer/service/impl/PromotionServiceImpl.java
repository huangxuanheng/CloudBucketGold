package me.zhengjie.gold.modules.consumer.service.impl;

import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.DataTransform;
import me.zhengjie.base.BaseRepository;
import me.zhengjie.gold.modules.consumer.domain.Promotion;
import me.zhengjie.gold.modules.consumer.repository.PromotionRepository;
import me.zhengjie.gold.modules.consumer.service.IPromotionService;
import me.zhengjie.gold.modules.consumer.service.dto.PromotionDto;
import org.springframework.stereotype.Service;

/**
 * author harry
 * dept: cloudpivot
 * date : 2020/7/23
 * des:
 */
@Service
@RequiredArgsConstructor
@DataTransform(dto = PromotionDto.class,entity = Promotion.class)
public class PromotionServiceImpl implements IPromotionService {
    private final PromotionRepository promotionRepository;
    @Override
    public BaseRepository getJpaRepository() {
        return promotionRepository;
    }
}
