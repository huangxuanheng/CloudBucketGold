package me.zhengjie.gold.modules.consumer.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import me.zhengjie.base.BaseDTO;

/**
 * author harry
 * dept: cloudpivot
 * date : 2020/7/17
 * des:
 */
@ApiModel
@Getter
@Setter
public class BaseDtoBy extends BaseDTO {
    @ApiModelProperty(value = "id")
    public Long id;

    @ApiModelProperty(value = "是否刪除")
    private Boolean isDel=false;
}
