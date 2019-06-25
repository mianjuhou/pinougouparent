package com.fs.shopweb.dao;

import com.fs.shopweb.pojo.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SpecificationDao extends JpaRepository<Specification, Long>, JpaSpecificationExecutor<Specification> {
}
