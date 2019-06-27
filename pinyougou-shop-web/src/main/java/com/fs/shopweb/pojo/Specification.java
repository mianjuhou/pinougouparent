package com.fs.shopweb.pojo;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "tb_specification")
@Data
public class Specification implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "spec_name")
    private String specName;

    @Transient
    private List<SpecificationOption> options;
}
