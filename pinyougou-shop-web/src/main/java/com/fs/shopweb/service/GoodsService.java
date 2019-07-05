package com.fs.shopweb.service;

import com.alibaba.fastjson.JSON;
import com.fs.common.entity.PageResult;
import com.fs.shopweb.dao.*;
import com.fs.shopweb.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class GoodsService {

    @Autowired
    private GoodsDao goodsDao;

    @Autowired
    private GoodsDescDao goodsDescDao;

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private ItemCatDao itemCatDao;

    @Autowired
    private SellerDao sellerDao;


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

//        String sellerId = "";
//        Seller seller = sellerDao.findById(sellerId).get();

        ItemCat itemCat = itemCatDao.findById(sGoods.getCategory3Id()).get();


        List<Item> items = goods.getItems();
        if (items != null && "1".equals(goods.getIsEnableSpec())) {
            items.forEach(item -> {
                String title = sGoods.getGoodsName();
                Map<String, String> specMap = JSON.parseObject(item.getSpec(), Map.class);
                for (Map.Entry<String, String> entry : specMap.entrySet()) {
                    title += " " + entry.getValue();
                }
                item.setTitle(title);

                List<Map> imageList = JSON.parseArray(goodsDesc.getItemImages(), Map.class);
                if (imageList != null && !imageList.isEmpty()) {
                    item.setImage((String) imageList.get(0).get("url"));
                }

                item.setCategoryId(sGoods.getCategory3Id());

                item.setCreateTime(new Date());
                item.setUpdateTime(new Date());

                item.setGoodsId(id);

//                item.setSellerId();
                item.setCategory(itemCat.getName());

//                item.setSeller(seller.getNickName());
                itemDao.save(item);
            });
        } else {
            Item item = new Item();
            item.setTitle(sGoods.getGoodsName());

            item.setPrice(sGoods.getPrice());
            item.setNum(999);

            List<Map> imageList = JSON.parseArray(goodsDesc.getItemImages(), Map.class);
            if (!imageList.isEmpty()) {
                item.setImage((String) imageList.get(0).get("url"));
            }

            item.setCategoryId(sGoods.getCategory3Id());

            item.setStatus("1");

            item.setCreateTime(new Date());
            item.setUpdateTime(new Date());

            item.setIsDefault("1");

            item.setGoodsId(id);

//                item.setSellerId();
            item.setCategory(itemCat.getName());

            item.setSpec("{}");

//                item.setSeller(seller.getNickName());
            itemDao.save(item);
        }
    }

    /**
     * 删
     */
    public void deleteById(long id) {
        goodsDao.updateDeleteStatus(id, "1");
    }

    /**
     * 改
     */
    public void update(Goods bean) {
        goodsDao.save(bean);
        goodsDescDao.save(bean.getGoodsDesc());
        //删除
        itemDao.deleteAllByGoodsId(bean.getId());
        //重新构建插入
        ItemCat itemCat = itemCatDao.findById(bean.getCategory3Id()).get();

        List<Item> items = bean.getItems();
        if (items != null && "1".equals(bean.getIsEnableSpec())) {
            items.forEach(item -> {
                item.setId(null);
                String title = bean.getGoodsName();
                Map<String, String> specMap = JSON.parseObject(item.getSpec(), Map.class);
                for (Map.Entry<String, String> entry : specMap.entrySet()) {
                    title += " " + entry.getValue();
                }
                item.setTitle(title);

                List<Map> imageList = JSON.parseArray(bean.getGoodsDesc().getItemImages(), Map.class);
                if (imageList != null && !imageList.isEmpty()) {
                    item.setImage((String) imageList.get(0).get("url"));
                }

                item.setCategoryId(bean.getCategory3Id());

                item.setCreateTime(new Date());
                item.setUpdateTime(new Date());

                item.setGoodsId(bean.getId());

//                item.setSellerId();
                item.setCategory(itemCat.getName());

//                item.setSeller(seller.getNickName());
                itemDao.save(item);
            });
        } else {
            Item item = new Item();
            item.setTitle(bean.getGoodsName());

            item.setPrice(bean.getPrice());
            item.setNum(999);

            List<Map> imageList = JSON.parseArray(bean.getGoodsDesc().getItemImages(), Map.class);
            if (!imageList.isEmpty()) {
                item.setImage((String) imageList.get(0).get("url"));
            }

            item.setCategoryId(bean.getCategory3Id());

            item.setStatus("1");

            item.setCreateTime(new Date());
            item.setUpdateTime(new Date());

            item.setIsDefault("1");

            item.setGoodsId(bean.getId());

//                item.setSellerId();
            item.setCategory(itemCat.getName());

            item.setSpec("{}");

//                item.setSeller(seller.getNickName());
            itemDao.save(item);
        }
    }

    /**
     * 查
     */
    /*ID查
     */
    public Goods findById(long id) {
        Goods goods = goodsDao.findById(id).get();

        GoodsDesc goodsDesc = goodsDescDao.findById(goods.getId()).get();
        goods.setGoodsDesc(goodsDesc);

        List<Item> items = itemDao.findALlByGoodsId(goods.getId());
        goods.setItems(items);

        return goods;
    }

    /*全部查
     */
    public List<Goods> findAll() {
        return goodsDao.findAll();
    }

    /*条件查
     */
    public List<Goods> findSearch(Goods bean) {
        return goodsDao.findAll(getSpecification(bean));
    }

    /*分页查
     */
    public PageResult<Goods> findPage(int pageSize, int pageNum, Goods bean) {
        Page<Goods> page = goodsDao.findAll(getSpecification(bean), PageRequest.of(pageNum - 1, pageSize));
        return new PageResult<>(page.getTotalElements(), page.getContent());
    }

    /*查询条件
     */
    public Specification<Goods> getSpecification(Goods bean) {
        return (Specification<Goods>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            //sellerId商家ID
            if (!StringUtils.isEmpty(bean.getSellerId())) {
                Predicate predicate = criteriaBuilder.equal(root.get("sellerId").as(String.class), bean.getSellerId());
                list.add(predicate);
            }
            //ngoodsName字段模糊匹配
            if (!StringUtils.isEmpty(bean.getGoodsName())) {
                Predicate predicate = criteriaBuilder.like(root.get("goodsName").as(String.class), "%" + bean.getGoodsName() + "%");
                list.add(predicate);
            }
            //auditStatus字段精确匹配
            if (!StringUtils.isEmpty(bean.getAuditStatus())) {
                Predicate predicate = criteriaBuilder.equal(root.get("auditStatus").as(String.class), bean.getAuditStatus());
                list.add(predicate);
            }
            //isMarketable字段精确匹配
            if (!StringUtils.isEmpty(bean.getIsMarketable())) {
                Predicate predicate = criteriaBuilder.equal(root.get("isMarketable").as(String.class), bean.getIsMarketable());
                list.add(predicate);
            }
            //caption字段模糊匹配
            if (!StringUtils.isEmpty(bean.getCaption())) {
                Predicate predicate = criteriaBuilder.like(root.get("caption").as(String.class), "%" + bean.getCaption() + "%");
                list.add(predicate);
            }
            //isEnableSpec字段精确匹配
            if (!StringUtils.isEmpty(bean.getIsEnableSpec())) {
                Predicate predicate = criteriaBuilder.equal(root.get("isEnableSpec").as(String.class), bean.getIsEnableSpec());
                list.add(predicate);
            }
            //所有数据都是没被标记为删除的
            Predicate deletePredicate = criteriaBuilder.isNull(root.get("isDelete").as(String.class));
            list.add(deletePredicate);

            Predicate[] predicates = new Predicate[list.size()];
            predicates = list.toArray(predicates);
            return criteriaBuilder.and(predicates);
        };
    }

    /**
     * 其他
     */
    /*修改审核状态
     */
    public void updateAuditStatus(Long id, String status) {
        goodsDao.updateAuditStatus(id, status);
    }

    /*批量修改审核状态
     */
    public void updateMutliAuditStatus(List<Long> ids, String status) {
        goodsDao.updateMutliAuditStatus(ids, status);
    }

    /*标记批量删除
     */
    public void deleteByIds(List<Long> ids) {
        goodsDao.updateMutliDeleteStatus(ids, "1");
    }

    /*批量上下架
     */
    public void updateBatchMarketable(List<Long> ids, String status) {
        goodsDao.updateBatchMarketable(ids, status);
    }
}
