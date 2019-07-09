package com.fs.content.controller;

import com.fs.common.entity.PageResult;
import com.fs.common.entity.Result;
import com.fs.common.entity.StatusCode;
import com.fs.content.pojo.ContentCategory;
import com.fs.content.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contentCategory")
@CrossOrigin
public class ContentCategoryController {

    public static final String MODULE_NAME = "广告分类";

    @Autowired
    private ContentCategoryService categoryService;

    /**
     * 增
     */
    @PostMapping
    public Result save(@RequestBody ContentCategory bean) {
        categoryService.save(bean);
        return new Result<>(true, StatusCode.OK, "保存" + MODULE_NAME + "成功");
    }

    /**
     * 删
     */
    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable Long id) {
        categoryService.deleteById(id);
        return new Result<>(true, StatusCode.OK, "删除" + MODULE_NAME + "成功");
    }

    /**
     * 改
     */
    @PutMapping
    public Result update(@RequestBody ContentCategory bean) {
        categoryService.update(bean);
        return new Result<>(true, StatusCode.OK, "修改" + MODULE_NAME + "成功");
    }

    /**
     * 查
     */
    /*ID查
     */
    @GetMapping("/{id}")
    public Result<ContentCategory> findById(@PathVariable("id") long id) {
        ContentCategory bean = categoryService.findById(id);
        return new Result<>(true, StatusCode.OK, "获取" + MODULE_NAME + "成功", bean);
    }

    /*全部查
     */
    @GetMapping
    public Result<List<ContentCategory>> findAll() {
        List<ContentCategory> beans = categoryService.findAll();
        return new Result(true, StatusCode.OK, "获取" + MODULE_NAME + "列表成功", beans);
    }

    /*条件查
     */
    @PostMapping("/search")
    public Result<List<ContentCategory>> findSearch(@RequestBody ContentCategory bean) {
        List<ContentCategory> beans = categoryService.findSearch(bean);
        return new Result(true, StatusCode.OK, "获取" + MODULE_NAME + "列表成功", beans);
    }

    /*分页查
     */
    @PostMapping("/search/{pageSize}/{pageNum}")
    public Result<PageResult<ContentCategory>> findPage(@PathVariable("pageSize") int pageSize, @PathVariable("pageNum") int pageNum, @RequestBody ContentCategory bean) {
        PageResult<ContentCategory> pageResult = categoryService.findPage(pageSize, pageNum, bean);
        return new Result(true, StatusCode.OK, "获取" + MODULE_NAME + "列表成功", pageResult);
    }

    /**
     * 其他
     */
    /*批量删除
     */
    @DeleteMapping("/batch")
    public Result deleteByIds(@RequestBody List<Long> ids) {
        categoryService.deleteByIds(ids);
        return new Result<>(true, StatusCode.OK, "批量删除" + MODULE_NAME + "成功");
    }


    @PostMapping("/testForm")
    public void testFormData(@RequestParam("questions_id") List<String> questions_id) {
        questions_id.forEach(qs -> {
            System.out.println(qs);
        });
    }

}
