package com.fs.shopweb.controller;

import com.fs.common.entity.PageResult;
import com.fs.common.entity.Result;
import com.fs.common.entity.StatusCode;
import com.fs.shopweb.pojo.TypeTemplate;
import com.fs.shopweb.service.TypeTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/typeTemplate")
@CrossOrigin
public class TypeTemplateController {

    @Autowired
    private TypeTemplateService service;


    //增
    @PostMapping
    public Result save(@RequestBody TypeTemplate bean) {
        service.save(bean);
        return new Result<>(true, StatusCode.OK, "保存品牌成功");
    }

    //删
    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable long id) {
        service.deleteById(id);
        return new Result<>(true, StatusCode.OK, "删除指定品牌成功");
    }

    //改
    @PutMapping("/{id}")
    public Result update(@PathVariable long id, @RequestBody TypeTemplate bean) {
        bean.setId(id);
        service.update(bean);
        return new Result<>(true, StatusCode.OK, "修改品牌成功");
    }

    //查
    //ID查
    @GetMapping("/{id}")
    public Result<TypeTemplate> findById(@PathVariable("id") long id) {
        TypeTemplate brand = service.findById(id);
        return new Result<>(true, StatusCode.OK, "获取指定品牌成功", brand);
    }

    //全部查
    @GetMapping
    public Result<List<TypeTemplate>> findAll() {
        List<TypeTemplate> brands = service.findAll();
        return new Result(true, StatusCode.OK, "获取品牌列表成功", brands);
    }


    //分页查
    @PostMapping("/search/{pageSize}/{pageNum}")
    public Result<PageResult<TypeTemplate>> findPage(@PathVariable("pageSize") int pageSize, @PathVariable("pageNum") int pageNum, @RequestBody TypeTemplate bean) {
        PageResult<TypeTemplate> pageResult = service.findPage(pageSize, pageNum, bean);
        return new Result(true, StatusCode.OK, "获取品牌列表成功", pageResult);
    }

    //其他
    @DeleteMapping("/deleteAll")
    public Result deleteAll(@RequestBody List<Long> ids) {
        service.deleteByIds(ids);
        return new Result(true, StatusCode.OK, "删除多条模板成功");
    }

}
