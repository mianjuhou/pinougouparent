package com.fs.shopweb.service;

import com.fs.common.entity.PageResult;
import com.fs.shopweb.dao.TypeTemplateDao;
import com.fs.shopweb.pojo.TypeTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TypeTemplateService {

    @Autowired
    private TypeTemplateDao dao;

    public void save(TypeTemplate bean) {
        dao.save(bean);
    }

    public void deleteById(long id) {
        dao.deleteById(id);
    }

    public void update(TypeTemplate bean) {
        dao.save(bean);
    }

    public List<TypeTemplate> findAll() {
        return dao.findAll();
    }

    public TypeTemplate findById(long id) {
        return dao.findById(id).get();
    }


    public PageResult<TypeTemplate> findPage(int pageSize, int pageNum, TypeTemplate bean) {
        Page<TypeTemplate> all = dao.findAll(getSpecification(bean), PageRequest.of(pageNum - 1, pageSize));
        return new PageResult<>(all.getTotalElements(), all.getContent());
    }

    public Specification<TypeTemplate> getSpecification(TypeTemplate bean) {
        return (Specification<TypeTemplate>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            if (!StringUtils.isEmpty(bean.getName())) {
                Predicate predicate = criteriaBuilder.like(root.get("name").as(String.class), "%" + bean.getName() + "%");
                list.add(predicate);
            }
            Predicate[] predicates = new Predicate[list.size()];
            predicates = list.toArray(predicates);
            return criteriaBuilder.and(predicates);
        };
    }

    public void deleteAll(List<Long> ids) {
        ids.forEach(id -> dao.deleteById(id));
    }
}
