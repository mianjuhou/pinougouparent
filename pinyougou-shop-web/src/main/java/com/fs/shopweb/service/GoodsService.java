package com.fs.shopweb.service;

import com.fs.shopweb.dao.GoodsDao;
import com.fs.shopweb.dao.GoodsDescDao;
import com.fs.shopweb.dao.ItemDao;
import com.fs.shopweb.pojo.Goods;
import com.fs.shopweb.pojo.GoodsDesc;
import com.fs.shopweb.pojo.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class GoodsService {

    @Autowired
    private GoodsDao goodsDao;

    @Autowired
    private GoodsDescDao goodsDescDao;

    @Autowired
    private ItemDao itemDao;

    /**
     * 增
     */
    public void save(Goods goods) {
        goods.setAuditStatus("0");
        Goods sGoods = goodsDao.save(goods);
        Long id = sGoods.getId();

        GoodsDesc goodsDesc = goods.getGoodsDesc();
        if (goodsDesc != null) {
            goodsDesc.setGoodsId(id);
            goodsDescDao.save(goodsDesc);
        }

        List<Item> items = goods.getItems();
        if (items != null) {
            items.forEach(item -> {
                item.setGoodsId(id);
                itemDao.save(item);
            });
        }
    }
}
