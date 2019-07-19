package com.fs.content.controller;

import com.fs.common.entity.PageResult;
import com.fs.common.entity.Result;
import com.fs.common.entity.StatusCode;
import com.fs.content.pojo.Content;
import com.fs.content.service.ContentCategoryService;
import com.fs.content.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/content")
@CrossOrigin
public class ContentController {

    public static final String MODULE_NAME = "广告";

    @Autowired
    private ContentService contentService;

    /**
     * 增
     */
    @PostMapping
    public Result save(@RequestBody Content bean) {
        contentService.save(bean);
        return new Result<>(true, StatusCode.OK, "保存" + MODULE_NAME + "成功");
    }

    /**
     * 删
     */
    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable Long id) {
        contentService.deleteById(id);
        return new Result<>(true, StatusCode.OK, "删除" + MODULE_NAME + "成功");
    }

    /**
     * 改
     */
    @PutMapping
    public Result update(@RequestBody Content bean) {
        contentService.update(bean);
        return new Result<>(true, StatusCode.OK, "修改" + MODULE_NAME + "成功");
    }

    /**
     * 查
     */
    /*ID查
     */
    @GetMapping("/{id}")
    public Result<Content> findById(@PathVariable("id") long id) {
        Content bean = contentService.findById(id);
        return new Result<>(true, StatusCode.OK, "获取" + MODULE_NAME + "成功", bean);
    }

    /*全部查
     */
    @GetMapping
    public Result<List<Content>> findAll() {
        List<Content> beans = contentService.findAll();
        return new Result(true, StatusCode.OK, "获取" + MODULE_NAME + "列表成功", beans);
    }

    /*条件查
     */
    @PostMapping("/search")
    public Result<List<Content>> findSearch(@RequestBody Content bean) {
        List<Content> beans = contentService.findSearch(bean);
        return new Result(true, StatusCode.OK, "获取" + MODULE_NAME + "列表成功", beans);
    }

    /*分页查
     */
    @PostMapping("/search/{pageSize}/{pageNum}")
    public Result<PageResult<Content>> findPage(@PathVariable("pageSize") int pageSize, @PathVariable("pageNum") int pageNum, @RequestBody Content bean) {
        PageResult<Content> pageResult = contentService.findPage(pageSize, pageNum, bean);
        return new Result(true, StatusCode.OK, "获取" + MODULE_NAME + "分页列表成功", pageResult);
    }

    /**
     * 其他
     */
    /*批量查找
     */
    @GetMapping("/batch")
    public Result findByIds(@RequestBody List<Long> ids) {
        List<Content> beans = contentService.findByIds(ids);
        return new Result(true, StatusCode.OK, "批量获取" + MODULE_NAME + "成功", beans);
    }

    /*批量删除
     */
    @DeleteMapping("/batch")
    public Result deleteByIds(@RequestBody List<Long> ids) {
        contentService.deleteByIds(ids);
        return new Result<>(true, StatusCode.OK, "批量删除" + MODULE_NAME + "成功");
    }

    /*批量更改状态
     */
    @PutMapping("/batch/{status}")
    public Result updateStatusByIds(@RequestBody List<Long> ids, @PathVariable("status") String status) {
        contentService.updateStatusByIds(ids, status);
        return new Result<>(true, StatusCode.OK, "批量修改" + MODULE_NAME + "状态成功");
    }

    /*根据分类ID查询
     */
    @GetMapping("/catId/{categoryId}")
    public Result findByCategoryId(@PathVariable("categoryId") Long categoryId) {
        List<Content> beans = contentService.findByCategoryId(categoryId);
        return new Result(true, StatusCode.OK, "获取" + MODULE_NAME + "列表成功", beans);
    }

}
