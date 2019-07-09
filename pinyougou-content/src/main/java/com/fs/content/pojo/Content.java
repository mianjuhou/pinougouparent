package com.fs.content.pojo;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tb_content")
@Data
public class Content implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "category_id")
    private Long categoryId;
    private String title;
    private String url;
    private String pic;
    private String status;
    @Column(name = "sort_order")
    private Long sortOrder;
}
