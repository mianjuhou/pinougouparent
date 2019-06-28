package com.fs.shopweb.controller;

import com.fs.common.entity.PageResult;
import com.fs.common.entity.Result;
import com.fs.common.entity.StatusCode;
import com.fs.shopweb.pojo.Specification;
import com.fs.shopweb.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/specification")
@CrossOrigin
public class SpecificationController {

    @Autowired
    private SpecificationService service;


    //增
    @PostMapping
    public Result save(@RequestBody Specification bean) {
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
    public Result update(@PathVariable long id, @RequestBody Specification bean) {
        bean.setId(id);
        service.update(bean);
        return new Result<>(true, StatusCode.OK, "修改品牌成功");
    }

    //查
    //ID查
    @GetMapping("/{id}")
    public Result<Specification> findById(@PathVariable("id") long id) {
        Specification brand = service.findById(id);
        return new Result<>(true, StatusCode.OK, "获取指定品牌成功", brand);
    }

    //全部查
    @GetMapping
    public Result<List<Specification>> findAll() {
        List<Specification> brands = service.findAll();
        return new Result(true, StatusCode.OK, "获取品牌列表成功", brands);
    }

    //分页查
    @PostMapping("/search/{pageSize}/{pageNum}")
    public Result<PageResult<Specification>> findPage(@PathVariable("pageSize") int pageSize, @PathVariable("pageNum") int pageNum, @RequestBody Specification bean) {
        PageResult<Specification> pageResult = service.findPage(pageSize, pageNum, bean);
        return new Result(true, StatusCode.OK, "分页获取规格列表成功", pageResult);
    }

    //其他

    /**
     * 插入或更新规格表 插入或更新规格选项表 因为分页就不返回了
     */
    @PostMapping("/saveAll")
    public Result saveAll(@RequestBody Specification specification) {
        service.saveAll(specification);
        return new Result(true, StatusCode.OK, "插入成功");
    }

    @DeleteMapping("/deleteAll")
    public Result deleteAll(@RequestBody List<Long> ids) {
        service.deleteByIds(ids);
        return new Result(true, StatusCode.OK, "级联删除成功");
    }

    @PutMapping("/updateAll/{id}")
    public Result updateAll(@PathVariable Long id,@RequestBody Specification specification) {
        service.updateAll(id,specification);
        return new Result(true, StatusCode.OK, "更新成功");
    }
}
