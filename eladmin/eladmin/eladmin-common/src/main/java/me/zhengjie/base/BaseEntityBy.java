package me.zhengjie.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * author harry
 * dept: cloudpivot
 * date : 2020/7/17
 * des:
 */
@Getter
@Setter
@MappedSuperclass
public class BaseEntityBy extends BaseEntity {
    @Id
    @Column(name = "id")
    @ApiModelProperty(value = "ID", hidden = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty(value = "是否刪除")
    private Boolean isDel;
}
