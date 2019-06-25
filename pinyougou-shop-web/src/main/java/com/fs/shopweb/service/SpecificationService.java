package com.fs.shopweb.service;

import com.fs.common.entity.PageResult;
import com.fs.shopweb.dao.SpecificationDao;
import com.fs.shopweb.pojo.Specification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class SpecificationService {

    @Autowired
    private SpecificationDao dao;

    public PageResult<Specification> findPage(int pageSize, int pageNum, Specification bean) {
        Page<Specification> all = dao.findAll(getSpecification(bean), PageRequest.of(pageNum - 1, pageSize));
        return new PageResult<>(all.getTotalElements(), all.getContent());
    }


    public org.springframework.data.jpa.domain.Specification<Specification> getSpecification(Specification bean) {
        return (org.springframework.data.jpa.domain.Specification<Specification>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            if (!StringUtils.isEmpty(bean.getSpec_name())) {
                Predicate predicate = criteriaBuilder.like(root.get("spec_name").as(String.class), "%" + bean.getSpec_name() + "%");
                list.add(predicate);
            }
            Predicate[] predicates = new Predicate[list.size()];
            predicates = list.toArray(predicates);
            return criteriaBuilder.and(predicates);
        };
    }
}
