package com.fs.shopweb.controller;

import com.fs.common.entity.Result;
import com.fs.common.entity.StatusCode;
import com.fs.shopweb.pojo.Brand;
import com.fs.shopweb.pojo.Goods;
import com.fs.shopweb.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("goods")
@CrossOrigin
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private HttpServletRequest request;

    /**
     * 增
     */
    @PostMapping
    public Result save(@RequestBody Goods goods) {
        //通过Requst获取商家ID
//        Object claims = request.getAttribute("claims_user");
//        String sellerId = claims.getId();
//        String sellerId = "8";
//        goods.setSellerId(sellerId);

        goodsService.save(goods);
        return new Result<>(true, StatusCode.OK, "保存商品成功");
    }

}
