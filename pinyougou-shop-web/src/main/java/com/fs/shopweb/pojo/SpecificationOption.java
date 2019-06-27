package com.fs.shopweb.pojo;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tb_specification_option")
@Data
public class SpecificationOption implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "option_name")
    private String optionName;
    @Column(name = "spec_id")
    private Long specId;
    private Integer orders;
}
