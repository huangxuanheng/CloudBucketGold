package me.zhengjie.gold.modules.consumer.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import me.zhengjie.base.BaseEntityBy;

import javax.persistence.Entity;
import javax.persistence.Table;
/**
 * @author Harry
 * @date 2020-07-18
 * @des 描述：消费商实体类
 */
@Entity
@Getter
@Setter
@Table(name="t_consumer")
public class Consumer extends BaseEntityBy {
    @ApiModelProperty(value = "上级ID，上级消费商")
    private long parentId;

    @ApiModelProperty(value = "消费商绑定的用户ID")
    private long userId;

    @ApiModelProperty(value = "级别ID")
    private long gradeId;


    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "子消费商数量")
    private int subCount;

    @ApiModelProperty(value = "查询编码")
    private String queryCode;
}
