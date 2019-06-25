package com.fs.shopweb.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "tb_brand")
@Data
public class Brand implements Serializable {
    @Id
    private Long id;
    private String name;
    private String first_char;
}