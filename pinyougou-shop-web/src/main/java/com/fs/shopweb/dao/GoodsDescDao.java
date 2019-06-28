package com.fs.shopweb.dao;

import com.fs.shopweb.pojo.Goods;
import com.fs.shopweb.pojo.GoodsDesc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GoodsDescDao extends JpaRepository<GoodsDesc, Long>, JpaSpecificationExecutor<GoodsDesc> {

}
