package me.zhengjie.gold.modules.consumer.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import me.zhengjie.modules.system.service.dto.UserDto;

import javax.validation.constraints.Min;
import java.util.List;

/**
 * @author Harry
 * @date 2020/7/18
 * @des 描述：消费商
 */
@Setter
@Getter
@ApiModel
public class ConsumerDto extends BaseDtoBy{

    @ApiModelProperty(value = "上级ID，上级消费商")
    @Min(value = 0,message = "没有指定上级")
    private Long parentId=0l;

    @ApiModelProperty(value = "消费商绑定的用户ID")
    @Min(value = 0,message = "没有指定用户")
    private Long userId=0l;

    @ApiModelProperty(value = "级别ID")
    @Min(value = 0,message = "没有指定级别")
    private Long gradeId=0l;


    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "子消费商数量")
    private int subCount;

    @ApiModelProperty(value = "是否叶子节点")
    private boolean isLeaf;

    @ApiModelProperty(value = "查询编码")
    private String queryCode="";

    @ApiModelProperty(value = "父消费商")
    private ConsumerDto parent;

    @ApiModelProperty(value = "子消费商集合")
    private List<ConsumerDto> childrens;

    @ApiModelProperty(value = "级别信息")
    private GradeDto grade;

    @ApiModelProperty(value = "用户信息")
    private UserDto user;


    public boolean isLeaf() {
        return subCount>0?false:true;
    }
}
