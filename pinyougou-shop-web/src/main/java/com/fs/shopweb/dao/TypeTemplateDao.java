package com.fs.shopweb.dao;

import com.fs.shopweb.pojo.TypeTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TypeTemplateDao extends JpaRepository<TypeTemplate, Long>, JpaSpecificationExecutor<TypeTemplate> {

    @Modifying
    @Query(value = "delete from tb_type_template where id in (:ids)", nativeQuery = true)
    void deleteByIds(@Param("ids") List<Long> ids);

}
