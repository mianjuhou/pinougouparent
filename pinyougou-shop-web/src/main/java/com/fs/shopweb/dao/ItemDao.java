package com.fs.shopweb.dao;

import com.fs.shopweb.pojo.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ItemDao extends JpaRepository<Item, Long>, JpaSpecificationExecutor<Item> {

    List<Item> findALlByGoodsId(Long goodsId);

    int deleteAllByGoodsId(Long goodsId);

}
