package com.fs.shopweb.controller;

import com.fs.common.entity.PageResult;
import com.fs.common.entity.Result;
import com.fs.common.entity.StatusCode;
import com.fs.shopweb.pojo.Seller;
import com.fs.shopweb.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;

@RestController
@RequestMapping("/seller")
@CrossOrigin
public class SellerController {
    @Autowired
    private SellerService service;

    @Autowired
    private HttpServletRequest request;

    /**
     * 增
     */
    @PostMapping
    public Result save(@RequestBody Seller bean) {
        service.save(bean);
        return new Result<>(true, StatusCode.OK, "保存品牌成功");
    }

    /**
     * 删
     */
    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable String id) {
        service.deleteById(id);
        return new Result<>(true, StatusCode.OK, "删除指定品牌成功");
    }

    /**
     * 改
     */
    @PutMapping("/{id}")
    public Result update(@PathVariable String id, @RequestBody Seller bean) {
        bean.setSellerId(id);
        service.update(bean);
        return new Result<>(true, StatusCode.OK, "修改品牌成功");
    }

    /**
     * 查
     */
    /*ID查
     */
    @GetMapping("/{id}")
    public Result<Seller> findById(@PathVariable("id") String id) {
        Seller brand = service.findById(id);
        return new Result<>(true, StatusCode.OK, "获取指定品牌成功", brand);
    }

    /*全部查
     */
    @GetMapping
    public Result<List<Seller>> findAll() {
        List<Seller> brands = service.findAll();
        return new Result(true, StatusCode.OK, "获取品牌列表成功", brands);
    }

    /*条件查
     */
    @PostMapping("/search")
    public Result<List<Seller>> findSearch(@RequestBody Seller bean) {
        List<Seller> datas = service.findSearch(bean);
        return new Result(true, StatusCode.OK, "获取品牌列表成功", datas);
    }

    /*分页查
     */
    @PostMapping("/search/{pageSize}/{pageNum}")
    public Result<PageResult<Seller>> findPage(@PathVariable("pageSize") int pageSize, @PathVariable("pageNum") int pageNum, @RequestBody Seller bean) {
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            System.out.println(name + ":" + request.getHeader(name));
        }
        PageResult<Seller> pageResult = service.findPage(pageSize, pageNum, bean);
        return new Result(true, StatusCode.OK, "获取品牌列表成功", pageResult);
    }

    /**
     * 其他
     */
    /*修改审核状态
     */
    @PutMapping("/status/{id}/{status}")
    public Result updateStatus(@PathVariable("id") String id, @PathVariable("status") String status) {
        service.updateStatus(id, status);
        return new Result(true, StatusCode.OK, "修改状态成功");
    }
}
