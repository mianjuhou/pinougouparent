package com.fs.shopweb.service;

import com.fs.common.entity.PageResult;
import com.fs.shopweb.dao.BrandDao;
import com.fs.shopweb.pojo.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class BrandService {

    @Autowired
    private BrandDao dao;

    public void save(Brand bean) {
        dao.save(bean);
    }

    public void deleteById(long id) {
        dao.deleteById(id);
    }

    public void update(Brand bean) {
        dao.save(bean);
    }

    public List<Brand> findAll() {
        return dao.findAll();
    }

    public Brand findById(long id) {
        return dao.findById(id).get();
    }

    public void deleteByIds(List<Long> ids) {
        dao.deleteByIds(ids);
    }

    public PageResult<Brand> findPage(int pageSize, int pageNum, Brand bean) {
        Page<Brand> page = dao.findAll(getSpecification(bean), PageRequest.of(pageNum - 1, pageSize));
        return new PageResult<>(page.getTotalElements(), page.getContent());
    }

    public List<Brand> findSearch(Brand bean) {
        return dao.findAll(getSpecification(bean));
    }

    public Specification<Brand> getSpecification(Brand bean) {
        return new Specification<Brand>() {
            @Override
            public Predicate toPredicate(Root<Brand> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                if (!StringUtils.isEmpty(bean.getName())) {
                    Predicate predicate = criteriaBuilder.like(root.get("name").as(String.class), "%" + bean.getName() + "%");
                    list.add(predicate);
                }
                if (!StringUtils.isEmpty(bean.getFirstChar())) {
                    Predicate predicate = criteriaBuilder.like(root.get("first_char").as(String.class), "%" + bean.getFirstChar() + "%");
                    list.add(predicate);
                }
                Predicate[] predicates = new Predicate[list.size()];
                predicates = list.toArray(predicates);
                return criteriaBuilder.and(predicates);
            }
        };
    }
}
