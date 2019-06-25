package com.fs.shopweb.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "tb_specification")
@Data
public class Specification implements Serializable {
    @Id
    private long id;
    private String spec_name;
}
