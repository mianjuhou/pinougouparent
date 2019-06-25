package com.fs.shopweb.dao;

import com.fs.shopweb.pojo.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BrandDao extends JpaRepository<Brand, Long>, JpaSpecificationExecutor<Brand> {

    @Modifying
    @Query(value = "delete from tb_brand where id in (:ids)", nativeQuery = true)
    void deleteByIds(@Param("ids") List<Long> ids);
}
