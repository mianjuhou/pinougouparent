package com.fs.shopweb.dao;

import com.fs.shopweb.pojo.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SpecificationDao extends JpaRepository<Specification, Long>, JpaSpecificationExecutor<Specification> {

    @Modifying
    @Query(value = "delete for tb_specification where id in (:ids)", nativeQuery = true)
    int deleteByIds(@Param("ids") List<Long> ids);
}
