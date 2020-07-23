package me.zhengjie.gold.modules.consumer.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import me.zhengjie.base.BaseEntityBy;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * author harry
 * dept: cloudpivot
 * date : 2020/7/23
 * des:促销方案
 */
@ApiModel
@Getter
@Setter
public class PromotionDto extends BaseDtoBy {
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "促销简介")
    private String introduct;
    @ApiModelProperty(value = "促销规则：1-满多少减多少，2-满多少打折，3-第几件半价")
    private int rule;
    @ApiModelProperty(value = "rule促销规则中满足的条件,金额单位是分，")
    private int condition;
    @ApiModelProperty(value = "促销规则：1时，表示减少金额(分)，2时，表示折扣（计算时需要除以100），3时，表示数量")
    private int reduce;
}
