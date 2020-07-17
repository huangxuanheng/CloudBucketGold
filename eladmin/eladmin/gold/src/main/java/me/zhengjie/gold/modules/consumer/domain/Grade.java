package me.zhengjie.gold.modules.consumer.domain;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import me.zhengjie.base.BaseEntityBy;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * author harry
 * dept: cloudpivot
 * date : 2020/7/17
 * des: 消费商等级
 */
@Entity
@Getter
@Setter
@Table(name="t_consumer_grade")
public class Grade extends BaseEntityBy {

    @ApiModelProperty(value = "等级名称")
    private String name;

    @ApiModelProperty(value = "佣金比例,消费商获得的实际佣金比例")
    private float commission;

    @ApiModelProperty(value = "备注")
    private String remark;
}
