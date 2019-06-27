package com.fs.shopweb.dao;

import com.fs.shopweb.pojo.SpecificationOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SpecificationOptionDao extends JpaRepository<SpecificationOption, Long>, JpaSpecificationExecutor<SpecificationOption> {

    @Query(value = "select * from tb_specification_option where spec_id = ? order by orders asc ",nativeQuery = true)
    List<SpecificationOption> findBySpecId(long specId);

    List<SpecificationOption> findAllBySpecIdOrderByOrdersAsc(long specId);

    int deleteAllBySpecId(long specId);
}
