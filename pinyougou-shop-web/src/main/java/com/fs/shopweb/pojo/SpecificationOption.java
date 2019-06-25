package com.fs.shopweb.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "tb_specification_option")
@Data
public class SpecificationOption implements Serializable {
    @Id
    private long id;
    private String option_name;
    private long spec_id;
    private int orders;
}
