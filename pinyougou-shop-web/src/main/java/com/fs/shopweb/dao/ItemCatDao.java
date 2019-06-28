package com.fs.shopweb.dao;

import com.fs.shopweb.pojo.ItemCat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;
import java.util.List;

public interface ItemCatDao extends JpaRepository<ItemCat, Long>, JpaSpecificationExecutor<ItemCat> {

    List<ItemCat> findAllByParentId(Long parentId);

    @Query(value = "select id from tb_item_cat where parent_id = ?", nativeQuery = true)
    List<BigInteger> findIdByParentId(Long parentId);


    @Modifying
    @Query(value = "delete from tb_item_cat where parent_id = :parentId", nativeQuery = true)
    int deleteByParentId(@Param("parentId") Long parentId);

    @Modifying
    @Query(value = "delete from tb_item_cat where id in (:ids) ", nativeQuery = true)
    int deleteByIds(@Param("ids") List<Long> ids);

}
