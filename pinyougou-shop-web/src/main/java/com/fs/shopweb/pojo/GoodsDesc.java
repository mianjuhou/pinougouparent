package com.fs.shopweb.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "tb_goods_desc")
@Data
public class GoodsDesc implements Serializable {

    @Id
    @Column(name = "goods_id")
    private Long goodsId;
    private String introduction;
    @Column(name = "specification_items")
    private String specificationItems;
    @Column(name = "custom_attribute_items")
    private String customAttributeItems;
    @Column(name = "item_images")
    private String itemImages;
    @Column(name = "package_list")
    private String packageList;
    @Column(name = "sale_service")
    private String saleService;
}
