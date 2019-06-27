package com.fs.shopweb.pojo;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tb_type_template")
@Data
public class TypeTemplate implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "spec_ids")
    private String specIds;
    @Column(name = "brand_ids")
    private String brandIds;
    @Column(name = "custom_attribute_items")
    private String customAttributeItems;
}
