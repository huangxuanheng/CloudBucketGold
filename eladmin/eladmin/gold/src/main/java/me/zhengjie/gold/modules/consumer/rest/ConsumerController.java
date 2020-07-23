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
        check(consumerDto);
        ConsumerDto save = consumerService.save(consumerDto);
        setQueryCODE(save);
        return success(ErrorMsg.SUCCESS);
    }

    /**
     * 将父节点子数量更新
     * @param parentId
     */
    private void setParentSubCount(Long parentId) {
        ConsumerDto parent = consumerService.get(parentId==null?0:parentId);
        if(parent!=null){
            List<ConsumerDto> childrens = consumerService.getChildrensByQueryCode(parent.getQueryCode());
            parent.setSubCount(childrens==null?0:childrens.size()-1);
            consumerService.update(parent);
        }
    }

    @Log("修改消费商")
    @ApiOperation("修改消费商")
    @PutMapping
    @PreAuthorize("@el.check('consumer:list')")
    public ResponseEntity modify(@RequestBody ConsumerDto consumerDto){
        check(consumerDto);
        consumerService.update(consumerDto);
        setQueryCODE(consumerDto);
        return success(ErrorMsg.SUCCESS);
    }

    private void setQueryCODE(ConsumerDto consumerDto) {
        //设置父节点子消费商数量
        if(consumerDto.getId()==null){
            throw new ConsumerExcetion(ErrorMsg.CONSUMER_ID_NOT_NULL);
        }
        ConsumerDto parent = consumerService.get(consumerDto.getParentId());
        if(parent!=null){
            consumerDto.setQueryCode(parent.getQueryCode()+consumerDto.getId()+"#");
        }else {
            consumerDto.setQueryCode("0#"+consumerDto.getId()+"#");
        }
        consumerService.update(consumerDto);
        setParentSubCount(consumerDto.getParentId());
//        List<ConsumerDto> childrens = consumerService.getChildrensByQueryCode(parent.getQueryCode());
//        parent.setSubCount(childrens==null?0:childrens.size()-1);
//        consumerService.update(parent);
    }

    /**
     * 检查父节点，消费商绑定的用户ID和上级消费商绑定的用户ID不能是同一个人
     * @param consumerDto
     */
    private void check(ConsumerDto consumerDto) {
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
//        boolean isEiditerFlag=false;
        if(consumerDto.getId()!=null){
            ConsumerDto baseDTO = consumerService.get(consumerDto.getId());
            if(baseDTO.getParentId().longValue()!=consumerDto.getParentId().longValue()){
                ConsumerDto parent = consumerService.get(consumerDto.getParentId());
                List<ConsumerDto> childrens = consumerService.getChildrensByParentId(consumerDto.getId());
                if(CollectionUtils.isNotEmpty(childrens)){
                    childrens.forEach(child->{
                        if(child.getUserId().longValue()==parent.getUserId().longValue()){
                            throw new ConsumerExcetion(ErrorMsg.UP_LINE_DONT_DOWN);
                        }
                    });
                }
            }
//            isEiditerFlag=baseDTO.getUserId().longValue()==consumerDto.getUserId()&&baseDTO.getParentId()==consumerDto.getParentId();
        }
        //检查当前绑定的用户是否已经成为消费商
        List<Consumer> consumers = consumerService.findByUserId(consumerDto.getUserId());
        if(CollectionUtils.isNotEmpty(consumers)){
            throw new ConsumerExcetion(ErrorMsg.CONSUMER_EXIST);
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
            consumerService.deleted(id);
            setParentSubCount(baseDTO.getParentId());
        }
        return success(null);
    }
}
