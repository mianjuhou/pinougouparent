package com.fs.shopweb.dao;

import com.fs.shopweb.pojo.TypeTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TypeTemplateDao extends JpaRepository<TypeTemplate, Long>, JpaSpecificationExecutor<TypeTemplate> {

}
