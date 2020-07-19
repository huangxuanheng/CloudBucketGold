package me.zhengjie.gold.modules.consumer.service.impl;

import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.DataTransform;
import me.zhengjie.base.BaseRepository;
import me.zhengjie.gold.modules.consumer.domain.Grade;
import me.zhengjie.gold.modules.consumer.repository.GradeRepository;
import me.zhengjie.gold.modules.consumer.service.IGradeService;
import me.zhengjie.gold.modules.consumer.service.dto.GradeDto;
import me.zhengjie.utils.ModelUtils;
import org.springframework.stereotype.Service;

/**
 * author harry
 * dept: cloudpivot
 * date : 2020/7/17
 * des:
 */
@Service
@RequiredArgsConstructor
@DataTransform(dto = GradeDto.class,entity = Grade.class)
public class GradeServiceImpl implements IGradeService {
    private final GradeRepository gradeRepository;
    @Override
    public BaseRepository getJpaRepository() {
        return gradeRepository;
    }
}
