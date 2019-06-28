package com.fs.shopweb.dao;

import com.fs.shopweb.pojo.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GoodsDao extends JpaRepository<Goods, Long>, JpaSpecificationExecutor<Goods> {

}
