package com.fs.content.dao;

import com.fs.content.pojo.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContentDao extends JpaRepository<Content, Long>, JpaSpecificationExecutor<Content> {

    @Modifying
    @Query(value = "delete from tb_content where id in (:ids)", nativeQuery = true)
    int deleteByIds(@Param("ids") List<Long> ids);

    @Modifying
    @Query(value = "update tb_content set status = :status where id in (:ids)", nativeQuery = true)
    int updateStatusByIds(@Param("ids")List<Long> ids,@Param("status") String status);
}
