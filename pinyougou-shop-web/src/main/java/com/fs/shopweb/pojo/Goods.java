package com.fs.shopweb.pojo;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "tb_goods")
@Data
public class Goods implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "seller_id")
    private String sellerId;
    @Column(name = "goods_name")
    private String goodsName;
    @Column(name = "default_item_id")
    private Long defaultItemId;
    @Column(name = "audit_status")
    private String auditStatus;
    @Column(name = "is_marketable")
    private String isMarketable;
    @Column(name = "brand_id")
    private Long brandId;
    private String caption;
    @Column(name = "category1_id")
    private Long category1Id;
    @Column(name = "category2_id")
    private Long category2Id;
    @Column(name = "category3_id")
    private Long category3Id;
    @Column(name = "small_pic")
    private String smallPic;
    private Double price;
    @Column(name = "type_template_id")
    private Long typeTemplateId;
    @Column(name = "is_enable_spec")
    private String isEnableSpec;
    @Column(name = "is_delete")
    private String isDelete;

    @Transient
    private GoodsDesc goodsDesc;

    @Transient
    private List<Item> items;
}
