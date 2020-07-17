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
import me.zhengjie.base.ResponseEntity;
import me.zhengjie.gold.modules.consumer.domain.Grade;
import me.zhengjie.gold.modules.consumer.service.IGradeService;
import me.zhengjie.gold.modules.consumer.service.dto.GradeDto;

import me.zhengjie.utils.ModelUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Harry
 * @date 2020-07-17
 */
@RestController
@RequiredArgsConstructor
@Api(tags = "消费商：级别管理")
@RequestMapping("/api/consumer")
@Slf4j
public class GradeController extends BaseController {

    private final IGradeService gradeService;


    @Log("查询级别")
    @ApiOperation("查询级别")
    @GetMapping(value = "/list")
    @PreAuthorize("@el.check('grade:list')")
    public ResponseEntity<List<Grade>> list(){
        List<Grade> all = gradeService.getAll();
        log.info(all.toString());
        return  success(all);
    }

    @Log("新增級別")
    @ApiOperation("查询级别")
    @PostMapping(value = "/add")
    @PreAuthorize("@el.check('grade:list')")
    public void add(@RequestBody GradeDto gradeDto){
        gradeService.save(gradeDto);
    }

}