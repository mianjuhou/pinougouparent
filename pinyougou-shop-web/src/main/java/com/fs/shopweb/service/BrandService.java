package com.fs.shopweb.service;

import com.fs.common.entity.PageResult;
import com.fs.shopweb.dao.BrandDao;
import com.fs.shopweb.pojo.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class BrandService {

    @Autowired
    private BrandDao dao;

    /**
     * 增
     */
    public void save(Brand bean) {
        dao.save(bean);
    }

    /**
     * 删
     */
    public void deleteById(long id) {
        dao.deleteById(id);
    }

    /**
     * 改
     */
    public void update(Brand bean) {
        dao.save(bean);
    }

    /**
     * 查
     */
    //ID查
    public Brand findById(long id) {
        return dao.findById(id).get();
    }

    //全部查
    public List<Brand> findAll() {
        return dao.findAll();
    }

    //条件查
    public List<Brand> findSearch(Brand bean) {
        return dao.findAll(getSpecification(bean));
    }

    //分页查
    public PageResult<Brand> findPage(int pageSize, int pageNum, Brand bean) {
        Page<Brand> page = dao.findAll(getSpecification(bean), PageRequest.of(pageNum - 1, pageSize));
        return new PageResult<>(page.getTotalElements(), page.getContent());
    }

    //查询条件
    public Specification<Brand> getSpecification(Brand bean) {
        return (Specification<Brand>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            //name字段模糊匹配
            if (!StringUtils.isEmpty(bean.getName())) {
                Predicate predicate = criteriaBuilder.like(root.get("name").as(String.class), "%" + bean.getName() + "%");
                list.add(predicate);
            }
            //first_char字段精确匹配
            if (!StringUtils.isEmpty(bean.getFirstChar())) {
                Predicate predicate = criteriaBuilder.equal(root.get("firstChar").as(String.class), bean.getFirstChar());
                list.add(predicate);
            }
            Predicate[] predicates = new Predicate[list.size()];
            predicates = list.toArray(predicates);
            return criteriaBuilder.and(predicates);
        };
    }

    /**
     * 其他
     */
    //ID多删除
    public void deleteByIds(List<Long> ids) {
        dao.deleteByIds(ids);
    }
}
