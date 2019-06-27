package com.fs.shopweb.dao;

import com.fs.shopweb.pojo.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SpecificationDao extends JpaRepository<Specification, Long>, JpaSpecificationExecutor<Specification> {

    int deleteAllByIdIn(List<Long> ids);

    @Query(value = "delete for tb_specification where id in (?)", nativeQuery = true)
    @Modifying
    int deleteByIds(List<Long> ids);
}
