package me.zhengjie.gold.modules.consumer.service.impl;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.DataTransform;
import me.zhengjie.base.BaseDTO;
import me.zhengjie.base.BaseRepository;
import me.zhengjie.gold.modules.consumer.domain.Consumer;
import me.zhengjie.gold.modules.consumer.repository.ConsumerRepository;
import me.zhengjie.gold.modules.consumer.service.IConsumerService;
import me.zhengjie.gold.modules.consumer.service.dto.ConsumerDto;
import me.zhengjie.utils.ModelUtils;
import me.zhengjie.utils.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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
        if(parentId==0){
            return ModelUtils.toModels(consumerRepository.findByParentId(parentId),ConsumerDto.class);
        }
        ConsumerDto baseDTO = get(parentId.longValue());
        return getChildrensByQueryCode(baseDTO.getQueryCode());
    }

    @Override
    public List<ConsumerDto> getChildrensByQueryCode(String queryCode) {
        if(StringUtils.isBlank(queryCode)){
            return null;
        }
        Specification specification=new Specification<Consumer>() {
                 @Override
                  public Predicate toPredicate(Root<Consumer> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                     Predicate query_code = criteriaBuilder.like(root.get("queryCode"), queryCode.concat("%"));
                     return  query_code;
                  }
              };
        List<Consumer> all = consumerRepository.findAll(specification);
        return ModelUtils.toModels(all,ConsumerDto.class);
    }

    @Override
    public List<Consumer> findByUserId(Long userId) {
        return consumerRepository.findByUserId(userId);
    }
}
