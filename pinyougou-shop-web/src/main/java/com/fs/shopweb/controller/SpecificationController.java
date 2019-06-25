package com.fs.shopweb.controller;

import com.fs.common.entity.PageResult;
import com.fs.common.entity.Result;
import com.fs.common.entity.StatusCode;
import com.fs.shopweb.pojo.Specification;
import com.fs.shopweb.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/specification")
@CrossOrigin
public class SpecificationController {

    @Autowired
    private SpecificationService service;

    //分页查
    @PostMapping("/search/{pageSize}/{pageNum}")
    public Result<PageResult<Specification>> findPage(@PathVariable("pageSize") int pageSize, @PathVariable("pageNum") int pageNum, @RequestBody Specification bean) {
        PageResult<Specification> pageResult = service.findPage(pageSize, pageNum, bean);
        return new Result(true, StatusCode.OK, "分页获取规格列表成功", pageResult);
    }

}
