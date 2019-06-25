package com.fs.shopweb.controller;

import com.fs.common.entity.PageResult;
import com.fs.common.entity.Result;
import com.fs.common.entity.StatusCode;
import com.fs.shopweb.pojo.Brand;
import com.fs.shopweb.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brand")
@CrossOrigin
public class BrandController {

    @Autowired
    private BrandService service;


    //增
    @PostMapping
    public Result save(@RequestBody Brand bean) {
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
    public Result update(@PathVariable long id, @RequestBody Brand bean) {
        bean.setId(id);
        service.update(bean);
        return new Result<>(true, StatusCode.OK, "修改品牌成功");
    }

    //查
    //ID查
    @GetMapping("/{id}")
    public Result<Brand> findById(@PathVariable("id") long id) {
        Brand brand = service.findById(id);
        return new Result<>(true, StatusCode.OK, "获取指定品牌成功", brand);
    }

    //全部查
    @GetMapping
    public Result<List<Brand>> findAll() {
        List<Brand> brands = service.findAll();
        return new Result(true, StatusCode.OK, "获取品牌列表成功", brands);
    }

    //条件查
    @PostMapping("/search")
    public Result<List<Brand>> findSearch(@RequestBody Brand bean) {
        List<Brand> datas = service.findSearch(bean);
        return new Result(true, StatusCode.OK, "获取品牌列表成功", datas);
    }

    //分页查
    @PostMapping("/search/{pageSize}/{pageNum}")
    public Result<PageResult<Brand>> findPage(@PathVariable("pageSize") int pageSize, @PathVariable("pageNum") int pageNum, @RequestBody Brand bean) {
        PageResult<Brand> pageResult = service.findPage(pageSize, pageNum, bean);
        return new Result(true, StatusCode.OK, "获取品牌列表成功", pageResult);
    }

    //其他
    @DeleteMapping("/ids")
    public Result deleteByIds(@RequestBody List<Long> ids) {
        service.deleteByIds(ids);
        return new Result<>(true, StatusCode.OK, "删除指定品牌成功");
    }


}
