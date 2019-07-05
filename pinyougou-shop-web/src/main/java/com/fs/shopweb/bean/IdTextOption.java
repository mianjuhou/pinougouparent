package com.fs.shopweb.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class IdTextOption<T> implements Serializable {
    private Long id;
    private String text;
    private List<T> options;
}
