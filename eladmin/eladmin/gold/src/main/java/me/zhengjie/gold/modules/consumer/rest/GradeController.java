/*
 *  Copyright 2019-2020 Zheng Jie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package me.zhengjie.gold.modules.consumer.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zhengjie.annotation.Log;
import me.zhengjie.base.BaseController;
import me.zhengjie.base.BaseDTO;
import me.zhengjie.exception.ConsumerExcetion;
import me.zhengjie.gold.modules.consumer.service.IGradeService;
import me.zhengjie.gold.modules.consumer.service.dto.GradeDto;
import me.zhengjie.utils.ErrorMsg;
import me.zhengjie.utils.PageUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author Harry
 * @date 2020-07-17
 */
@RestController
@RequiredArgsConstructor
@Api(tags = "消费商：级别管理")
@RequestMapping("/api/grade")
@Slf4j
public class GradeController extends BaseController {

    private final IGradeService gradeService;


    @Log("查询级别")
    @ApiOperation("查询级别")
    @GetMapping(value = "/list")
    @PreAuthorize("@el.check('grade:list')")
    public ResponseEntity< Map<String, Object>> list(){
        List<GradeDto> all = gradeService.getAll();
        log.info(all.toString());
        Map<String, Object> page = PageUtil.toPage(all, all.size());
        return  success(page);
    }

    @Log("新增級別")
    @ApiOperation("查询级别")
    @PostMapping
    @PreAuthorize("@el.check('grade:list')")
    public ResponseEntity add(@RequestBody GradeDto gradeDto){
        gradeService.save(gradeDto);
        return success(ErrorMsg.SUCCESS);
    }

    @Log("修改級別")
    @ApiOperation("修改級別")
    @PutMapping
    @PreAuthorize("@el.check('grade:list')")
    public ResponseEntity modify(@RequestBody GradeDto gradeDto){
        gradeService.update(gradeDto);
        return success(ErrorMsg.SUCCESS);
    }

    @Log("根据ID查询級別")
    @ApiOperation("根据ID查询級別")
    @GetMapping
    @PreAuthorize("@el.check('grade:list')")
    public ResponseEntity<BaseDTO> get(Long id){
        BaseDTO baseDTO = gradeService.get(id);
        return success(baseDTO);
    }


    @Log("根据ID删除級別")
    @ApiOperation("根据ID删除級別")
    @DeleteMapping
    @PreAuthorize("@el.check('grade:list')")
    public ResponseEntity<BaseDTO> del(@RequestParam("ids") Long []ids){
        if(ids.length==0){
            throw new ConsumerExcetion(ErrorMsg.ERR_ARR_PARAM_LEN);
        }
        for (Long id:ids){
            gradeService.deleted(id);
        }
        return success(null);
    }

}