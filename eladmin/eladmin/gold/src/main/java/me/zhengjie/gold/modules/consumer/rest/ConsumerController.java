package me.zhengjie.gold.modules.consumer.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zhengjie.annotation.Log;
import me.zhengjie.base.BaseController;
import me.zhengjie.base.BaseDTO;
import me.zhengjie.exception.ConsumerExcetion;
import me.zhengjie.gold.modules.consumer.service.IConsumerService;
import me.zhengjie.gold.modules.consumer.service.IGradeService;
import me.zhengjie.gold.modules.consumer.service.dto.ConsumerDto;
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
@Api(tags = "消费商：消费商管理")
@RequestMapping("/api/consumer")
public class ConsumerController extends BaseController {
    private final IConsumerService consumerService;

    private final IGradeService gradeService;

    private final UserService userService;

    @Log("查询消费商")
    @ApiOperation("查询消费商")
    @GetMapping(value = "/list")
    @PreAuthorize("@el.check('consumer:list')")
    public ResponseEntity< Map<String, Object>> list(){
        List<ConsumerDto> all = consumerService.getAll();
        for (ConsumerDto consumerDto:all){
            setConsumerRelated(consumerDto);
        }
        Map<String, Object> page = PageUtil.toPage(all, all.size());
        return  success(page);
    }

    @Log("新增消费商")
    @ApiOperation("查询消费商")
    @PostMapping
    @PreAuthorize("@el.check('consumer:list')")
    public ResponseEntity add(@RequestBody @Valid ConsumerDto consumerDto){
        checkParent(consumerDto);
        initBaseData(consumerDto);
        consumerService.save(consumerDto);
        return success(ErrorMsg.SUCCESS);
    }

    /**
     * 将父节点子数量更新
     * @param parentId
     * @param refreshNum
     */
    private void setParentSubCount(Long parentId,int refreshNum) {
        ConsumerDto parent = consumerService.get(parentId==null?0:parentId);
        if(parent!=null){
            List<ConsumerDto> childrens = consumerService.getChildrensByParentId(parentId);
            parent.setSubCount(childrens==null?0:childrens.size()+refreshNum);
            consumerService.update(parent);
        }
    }

    @Log("修改消费商")
    @ApiOperation("修改消费商")
    @PutMapping
    @PreAuthorize("@el.check('consumer:list')")
    public ResponseEntity modify(@RequestBody ConsumerDto consumerDto){
        checkParent(consumerDto);
        initBaseData(consumerDto);

        consumerService.update(consumerDto);
        return success(ErrorMsg.SUCCESS);
    }

    private void initBaseData(ConsumerDto consumerDto) {
        //设置父节点子消费商数量
        setParentSubCount(consumerDto.getParentId(),1);
        ConsumerDto parent = consumerService.get(consumerDto.getParentId());
        if(parent!=null){
            consumerDto.setQueryCode(parent.getQueryCode()+parent.getParentId()+"#");
        }else {
            consumerDto.setQueryCode(consumerDto.getParentId()+"#");
        }
    }

    /**
     * 检查父节点，消费商绑定的用户ID和上级消费商绑定的用户ID不能是同一个人
     * @param consumerDto
     */
    private void checkParent(ConsumerDto consumerDto) {
        if(consumerDto.getParentId()==null||consumerDto.getParentId().longValue()==0){
            //父是admin
            consumerDto.setParentId(0l);
        }else {
            //检测父节点和当前绑定的节点是否同一个人
            ConsumerDto parent = consumerService.get(consumerDto.getParentId());
            UserDto userDto = userService.findById(parent.getUserId());
            if(consumerDto.getUserId().longValue()==userDto.getId().longValue()){
                throw new ConsumerExcetion(ErrorMsg.PARENT_DONT_SELF);
            }
        }

        //检查父节点，是否存在当前绑定人的下级消费商
        checkParentIsExistCurrentEditer(consumerDto);

        //检查当前编辑的消费商的上级消费商，是否是自己的下级
        if(consumerDto.getId()!=null){
            ConsumerDto baseDTO = consumerService.get(consumerDto.getId());
            if(baseDTO.getParentId().longValue()!=consumerDto.getParentId().longValue()){
                List<ConsumerDto> childrens = consumerService.getChildrensByParentId(consumerDto.getId());
                if(CollectionUtils.isNotEmpty(childrens)){
                    childrens.forEach(child->{
                        if(child.getUserId().longValue()==consumerDto.getUserId().longValue()){
                            throw new ConsumerExcetion(ErrorMsg.UP_LINE_DONT_DOWN);
                        }
                    });
                }
            }
        }

    }

    private void checkParentIsExistCurrentEditer(ConsumerDto consumerDto) {
        List<ConsumerDto> childrens = consumerService.getChildrensByParentId(consumerDto.getParentId());
        if(CollectionUtils.isNotEmpty(childrens)){
            childrens.forEach(child->{
                if(child.getUserId().longValue()==consumerDto.getUserId().longValue()){
                    throw new ConsumerExcetion(ErrorMsg.SUB_CONSUMER_HAS_SELF);
                }
            });
        }
    }


    @Log("根据ID查询消费商")
    @ApiOperation("根据ID查询消费商")
    @GetMapping
    @PreAuthorize("@el.check('consumer:list')")
    public ResponseEntity<ConsumerDto> get(Long id){
        if(id==0){
            return success(null);
        }
        ConsumerDto baseDTO = consumerService.get(id);

        setConsumerRelated(baseDTO);

        return success(baseDTO);
    }

    /**
     * 设置消费商关联数据
     * @param baseDTO
     */
    private void setConsumerRelated(ConsumerDto baseDTO) {
        UserDto userDto = userService.findById(baseDTO.getUserId());
        baseDTO.setUser(userDto);
        //父节点
        ConsumerDto parent = consumerService.get(baseDTO.getParentId());
        baseDTO.setParent(parent);
        if(parent!=null){
            userDto = userService.findById(parent.getUserId());
            parent.setUser(userDto);
        }

        if(!baseDTO.isLeaf()&&baseDTO.getParentId()!=0){
            //子消费商
            List<ConsumerDto> consumerDtoList = consumerService.getChildrensByParentId(baseDTO.getParentId());
            baseDTO.setChildrens(consumerDtoList);
        }
        //级别
        GradeDto gradeDto = gradeService.get(baseDTO.getGradeId());
        baseDTO.setGrade(gradeDto);
    }


    @Log("根据ID删除消费商")
    @ApiOperation("根据ID删除消费商")
    @DeleteMapping
    @PreAuthorize("@el.check('consumer:list')")
    public ResponseEntity<BaseDTO> del(@RequestBody Long []ids){
        if(ids.length==0){
            throw new ConsumerExcetion(ErrorMsg.ERR_ARR_PARAM_LEN);
        }
        for (Long id:ids){
            ConsumerDto baseDTO = consumerService.get(id);
            if(baseDTO.getSubCount()>0){
                throw new ConsumerExcetion(ErrorMsg.HAS_SUB_CONSUMER);
            }
            setParentSubCount(baseDTO.getParentId(),-1);
            consumerService.deleted(id);
        }
        return success(null);
    }
}
