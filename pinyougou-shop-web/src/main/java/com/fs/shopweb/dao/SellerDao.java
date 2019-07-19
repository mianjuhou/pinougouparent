package com.fs.shopweb.dao;

import com.fs.shopweb.pojo.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SellerDao extends JpaRepository<Seller, String>, JpaSpecificationExecutor<Seller> {

    @Modifying
    @Query(value = "update tb_seller set status = :status where seller_id = :id", nativeQuery = true)
    int updateStatus(@Param("id") String id, @Param("status") String status);
}
