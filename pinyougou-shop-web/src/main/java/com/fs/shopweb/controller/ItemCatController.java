package com.fs.shopweb.controller;

import com.fs.common.entity.PageResult;
import com.fs.common.entity.Result;
import com.fs.common.entity.StatusCode;
import com.fs.shopweb.dao.ItemCatDao;
import com.fs.shopweb.pojo.ItemCat;
import com.fs.shopweb.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/itemCat")
@CrossOrigin
public class ItemCatController {

    @Autowired
    private ItemCatService service;

    /**
     * 增
     */
    @PostMapping
    public Result save(@RequestBody ItemCat bean) {
        service.save(bean);
        return new Result<>(true, StatusCode.OK, "保存成功");
    }

    /**
     * 删
     */
    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable long id) {
        service.deleteById(id);
        return new Result<>(true, StatusCode.OK, "删除成功");
    }

    /**
     * 改
     */
    @PutMapping("/{id}")
    public Result update(@PathVariable long id, @RequestBody ItemCat bean) {
        bean.setId(id);
        service.update(bean);
        return new Result<>(true, StatusCode.OK, "修改成功");
    }

    /**
     * 查
     */
    //ID查
    @GetMapping("/{id}")
    public Result<ItemCat> findById(@PathVariable("id") long id) {
        ItemCat brand = service.findById(id);
        return new Result<>(true, StatusCode.OK, "获取指定数据成功", brand);
    }

    //全部查
    @GetMapping
    public Result<List<ItemCat>> findAll() {
        List<ItemCat> brands = service.findAll();
        return new Result(true, StatusCode.OK, "获取列表数据成功", brands);
    }

    //条件查
    @PostMapping("/search")
    public Result<List<ItemCat>> findSearch(@RequestBody ItemCat bean) {
        List<ItemCat> datas = service.findSearch(bean);
        return new Result(true, StatusCode.OK, "获取列表数据成功", datas);
    }

    //分页查
    @PostMapping("/search/{pageSize}/{pageNum}")
    public Result<PageResult<ItemCat>> findPage(@PathVariable("pageSize") int pageSize, @PathVariable("pageNum") int pageNum, @RequestBody ItemCat bean) {
        PageResult<ItemCat> pageResult = service.findPage(pageSize, pageNum, bean);
        return new Result(true, StatusCode.OK, "获取列表数据成功", pageResult);
    }

    /**
     * 其他
     */
    /*多ID级联删除
     */
    @DeleteMapping("/ids/cascade")
    public Result deleteCascadeByIds(@RequestBody List<Long> ids) {
        service.deleteCascadeByIds(ids);
        return new Result<>(true, StatusCode.OK, "分类级联删除成功");
    }

}
