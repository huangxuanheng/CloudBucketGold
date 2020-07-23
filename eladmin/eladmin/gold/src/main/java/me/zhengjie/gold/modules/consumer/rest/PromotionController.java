package me.zhengjie.gold.modules.consumer.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.Log;
import me.zhengjie.base.BaseController;
import me.zhengjie.base.BaseDTO;
import me.zhengjie.exception.ConsumerExcetion;
import me.zhengjie.gold.modules.consumer.domain.Consumer;
import me.zhengjie.gold.modules.consumer.service.IConsumerService;
import me.zhengjie.gold.modules.consumer.service.IGradeService;
import me.zhengjie.gold.modules.consumer.service.IPromotionService;
import me.zhengjie.gold.modules.consumer.service.dto.PromotionDto;
import me.zhengjie.gold.modules.consumer.service.dto.GradeDto;
import me.zhengjie.modules.system.service.UserService;
import me.zhengjie.modules.system.service.dto.UserDto;
import me.zhengjie.utils.ErrorMsg;
import me.zhengjie.utils.PageUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author Harry
 * @date 2020/7/18
 * @des 描述：
 */
@RestController
@RequiredArgsConstructor
@Api(tags = "消费商：促销管理")
@RequestMapping("/api/promotion")
public class PromotionController extends BaseController {
    private final IPromotionService consumerService;

    @Log("查询促销")
    @ApiOperation("查询促销")
    @GetMapping(value = "/list")
    @PreAuthorize("@el.check('promotion:list')")
    public ResponseEntity< Map<String, Object>> list(){
        List<PromotionDto> all = consumerService.getAll();
        Map<String, Object> page = PageUtil.toPage(all, all.size());
        return  success(page);
    }

    @Log("新增促销")
    @ApiOperation("查询促销")
    @PostMapping
    @PreAuthorize("@el.check('consumer:list')")
    public ResponseEntity add(@RequestBody @Valid PromotionDto PromotionDto){
        PromotionDto save = consumerService.save(PromotionDto);
        return success(ErrorMsg.SUCCESS);
    }


    @Log("修改促销")
    @ApiOperation("修改促销")
    @PutMapping
    @PreAuthorize("@el.check('consumer:list')")
    public ResponseEntity modify(@RequestBody PromotionDto PromotionDto){
        consumerService.update(PromotionDto);
        return success(ErrorMsg.SUCCESS);
    }


    @Log("根据ID查询促销")
    @ApiOperation("根据ID查询促销")
    @GetMapping
    @PreAuthorize("@el.check('consumer:list')")
    public ResponseEntity<PromotionDto> get(Long id){
        if(id==0){
            return success(null);
        }
        PromotionDto baseDTO = consumerService.get(id);
        return success(baseDTO);
    }



    @Log("根据ID删除促销")
    @ApiOperation("根据ID删除促销")
    @DeleteMapping
    @PreAuthorize("@el.check('consumer:list')")
    public ResponseEntity<BaseDTO> del(@RequestBody Long []ids){
        if(ids.length==0){
            throw new ConsumerExcetion(ErrorMsg.ERR_ARR_PARAM_LEN);
        }
        for (Long id:ids){
            consumerService.deleted(id);
        }
        return success(null);
    }
}
