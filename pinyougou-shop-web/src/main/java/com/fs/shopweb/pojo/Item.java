package com.fs.shopweb.pojo;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tb_item")
@Data
public class Item implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(name = "sell_point")
    private String sellPoint;
    private Double price;
    @Column(name = "stock_count")
    private Integer stockCount;
    private Integer num;
    private String barcode;
    private String image;
    private String categoryId;
    private String status;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "update_time")
    private Date updateTime;
    @Column(name = "item_sn")
    private String itemSn;
    @Column(name = "cost_pirce")
    private Double costPirce;
    @Column(name = "market_price")
    private Double marketPrice;
    @Column(name = "is_default")
    private String isDefault;
    @Column(name = "goods_id")
    private Long goodsId;
    @Column(name = "seller_id")
    private String sellerId;
    @Column(name = "cart_thumbnail")
    private String cartThumbnail;
    private String category;
    private String brand;
    private String spec;
    private String seller;
}
