package com.fs.shopweb.controller;

import com.fs.common.entity.Result;
import com.fs.common.entity.StatusCode;
import com.fs.shopweb.pojo.SpecificationOption;
import com.fs.shopweb.service.SpecificationOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/specificationOption")
@CrossOrigin
public class SpecificationOptionController {

    @Autowired
    private SpecificationOptionService service;

    @GetMapping("/spec/{specId}")
    public Result<List<SpecificationOption>> findBySpecId(@PathVariable("specId") long specId) {
        List<SpecificationOption> options = service.findBySpecId(specId);
        return new Result<>(true, StatusCode.OK, "获取规格选项列表成功", options);
    }

}
