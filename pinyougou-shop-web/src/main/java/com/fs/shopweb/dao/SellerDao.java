package com.fs.shopweb.dao;

import com.fs.shopweb.pojo.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SellerDao extends JpaRepository<Seller, String>, JpaSpecificationExecutor<Seller> {

}
