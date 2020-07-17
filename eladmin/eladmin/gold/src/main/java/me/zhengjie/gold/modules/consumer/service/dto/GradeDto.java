package me.zhengjie.gold.modules.consumer.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * author harry
 * dept: cloudpivot
 * date : 2020/7/17
 * des:
 */
@Setter
@Getter
@ApiModel
public class GradeDto extends BaseDtoBy {

    @ApiModelProperty(value = "等级名称")
    private String name;

    @ApiModelProperty(value = "佣金比例,消费商获得的实际佣金比例")
    private float commission;

    @ApiModelProperty(value = "备注")
    private String remark;
}
