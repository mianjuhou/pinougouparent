package com.fs.shopweb.controller;

import com.fs.common.entity.PageResult;
import com.fs.common.entity.Result;
import com.fs.common.entity.StatusCode;
import com.fs.shopweb.pojo.Goods;
import com.fs.shopweb.service.GoodsService;
import com.fs.shopweb.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("goods")
@CrossOrigin
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private PictureService pictureService;

    /**
     * 增
     */
    @PostMapping
    public Result save(@RequestBody Goods goods) {
        //通过Requst获取商家ID
//        Object claims = request.getAttribute("claims_user");
//        String sellerId = claims.getId();
        String sellerId = "qiandu";
        goods.setSellerId(sellerId);

        goodsService.save(goods);
        return new Result<>(true, StatusCode.OK, "保存商品成功");
    }

    /**
     * 删
     */
    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable long id) {
        goodsService.deleteById(id);
        return new Result<>(true, StatusCode.OK, "删除指定商品成功");
    }

    /**
     * 改
     */
    @PutMapping
    public Result update(@RequestBody Goods bean) {
        goodsService.update(bean);
        return new Result<>(true, StatusCode.OK, "修改商品成功");
    }

    /**
     * 查
     */
    /*ID查
     */
    @GetMapping("/{id}")
    public Result<Goods> findById(@PathVariable("id") long id) {
        Goods bean = goodsService.findById(id);
        return new Result<>(true, StatusCode.OK, "获取指定商品成功", bean);
    }

    /*全部查
     */
    @GetMapping
    public Result<List<Goods>> findAll() {
        List<Goods> beans = goodsService.findAll();
        return new Result(true, StatusCode.OK, "获取商品列表成功", beans);
    }

    /*条件查
     */
    @PostMapping("/search")
    public Result<List<Goods>> findSearch(@RequestBody Goods bean) {
        List<Goods> datas = goodsService.findSearch(bean);
        return new Result(true, StatusCode.OK, "获取商品列表成功", datas);
    }

    /*分页查
     */
    @PostMapping("/search/{pageSize}/{pageNum}")
    public Result<PageResult<Goods>> findPage(@PathVariable("pageSize") int pageSize, @PathVariable("pageNum") int pageNum, @RequestBody Goods bean) {
        PageResult<Goods> pageResult = goodsService.findPage(pageSize, pageNum, bean);
        return new Result(true, StatusCode.OK, "获取商品列表成功", pageResult);
    }


    /**
     * 其他
     */
    /*上传图片
     */
    @PostMapping("/uploadImages")
    public Result<String> testUpload(@RequestBody MultipartFile file) {
        String url = pictureService.uploadPic(file);
        return new Result<>(true, StatusCode.OK, "成功", url);
    }

    /*删除图片
     */
    @PostMapping("/deleteImage")
    public Result deleteImage(@RequestBody String path) {
        pictureService.deletePic(path);
        return new Result<>(true, StatusCode.OK, "删除图片成功");
    }

    /*修改审核状态
     */
    @PutMapping("/auditStatus/{id}/{status}")
    public Result updateAuditStatus(@PathVariable("id") Long id,@PathVariable("status") String status){
        goodsService.updateAuditStatus(id,status);
        return new Result<>(true, StatusCode.OK, "修改状态成功");
    }

    /*批量修改商品的审核状态
     */
    @PutMapping("/auditStatus/{status}")
    public Result updateMutliAuditStatus(@RequestBody List<Long> ids,@PathVariable("status") String status){
        goodsService.updateMutliAuditStatus(ids,status);
        return new Result<>(true, StatusCode.OK, "修改状态成功");
    }

    /*标记批量删除
     */
    @DeleteMapping("/ids")
    public Result deleteByIds(@RequestBody List<Long> ids) {
        goodsService.deleteByIds(ids);
        return new Result<>(true, StatusCode.OK, "批量删除成功");
    }

    /*批量上下架
     */
    @PutMapping("/marketable/{status}")
    public Result updateBatchMarketable(@RequestBody List<Long> ids,@PathVariable("status") String status){
        goodsService.updateBatchMarketable(ids,status);
        return new Result<>(true, StatusCode.OK, "批量修改上下架状态成功");
    }

}
