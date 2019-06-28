package com.fs.shopweb.pojo;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tb_item_cat")
@Data
public class ItemCat implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "parent_id")
    private Long parentId;
    private String name;
    @Column(name = "type_id")
    private Long typeId;
}
