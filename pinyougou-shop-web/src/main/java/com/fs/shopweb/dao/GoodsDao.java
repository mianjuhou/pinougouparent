package com.fs.shopweb.dao;

import com.fs.shopweb.pojo.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GoodsDao extends JpaRepository<Goods, Long>, JpaSpecificationExecutor<Goods> {

    @Modifying
    @Query(value = "update tb_goods set audit_status = :status where id = :id",nativeQuery = true)
    int updateAuditStatus(@Param("id") Long id,@Param("status") String status);


    @Modifying
    @Query(value = "update tb_goods set audit_status = :status where id in (:ids)",nativeQuery = true)
    int updateMutliAuditStatus(@Param("ids") List<Long> ids, @Param("status") String status);

    @Modifying
    @Query(value = "update tb_goods set is_delete = :status where id = :id",nativeQuery = true)
    int updateDeleteStatus(@Param("id") Long id,@Param("status") String status);

    @Modifying
    @Query(value = "update tb_goods set is_delete = :status where id in (:ids)",nativeQuery = true)
    int updateMutliDeleteStatus(@Param("ids") List<Long> ids, @Param("status") String status);

    @Modifying
    @Query(value = "update tb_goods set is_marketable = :status where id in (:ids)",nativeQuery = true)
    int updateBatchMarketable(@Param("ids") List<Long> ids, @Param("status") String status);

}
