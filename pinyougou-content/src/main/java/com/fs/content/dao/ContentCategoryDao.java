package com.fs.content.dao;

import com.fs.content.pojo.ContentCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContentCategoryDao extends JpaRepository<ContentCategory, Long>, JpaSpecificationExecutor<ContentCategory> {

    @Modifying
    @Query(value = "delete from tb_content_category where id in (:ids)", nativeQuery = true)
    int deleteByIds(@Param("ids") List<Long> ids);

}
