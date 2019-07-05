package com.fs.shopweb.service;

import com.alibaba.fastjson.JSON;
import com.fs.common.entity.PageResult;
import com.fs.shopweb.bean.IdTextOption;
import com.fs.shopweb.dao.SpecificationOptionDao;
import com.fs.shopweb.dao.TypeTemplateDao;
import com.fs.shopweb.pojo.SpecificationOption;
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

    @Autowired
    private SpecificationOptionDao optionDao;

    /**
     * 增
     */
    public void save(TypeTemplate bean) {
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
    public void update(TypeTemplate bean) {
        dao.save(bean);
    }

    /**
     * 查
     */
    //ID查
    public TypeTemplate findById(long id) {
        return dao.findById(id).get();
    }

    //全部查
    public List<TypeTemplate> findAll() {
        return dao.findAll();
    }

    //条件查
    public List<TypeTemplate> findSearch(TypeTemplate bean) {
        return dao.findAll(getSpecification(bean));
    }

    //分夜查
    public PageResult<TypeTemplate> findPage(int pageSize, int pageNum, TypeTemplate bean) {
        Page<TypeTemplate> all = dao.findAll(getSpecification(bean), PageRequest.of(pageNum - 1, pageSize));
        return new PageResult<>(all.getTotalElements(), all.getContent());
    }

    //查询条件
    public Specification<TypeTemplate> getSpecification(TypeTemplate bean) {
        return (Specification<TypeTemplate>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            //name模糊匹配
            if (!StringUtils.isEmpty(bean.getName())) {
                Predicate predicate = criteriaBuilder.like(root.get("name").as(String.class), "%" + bean.getName() + "%");
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
    /*ID多删除
     */
    public void deleteByIds(List<Long> ids) {
        dao.deleteByIds(ids);
    }

    /*查询规格的全部信息
     */
    public List<IdTextOption> findSpecInfoById(long id) {
        TypeTemplate typeTemplate = dao.findById(id).get();
        List<IdTextOption> beans = JSON.parseArray(typeTemplate.getSpecIds(), IdTextOption.class);
        beans.forEach(bean -> {
            List<SpecificationOption> options = optionDao.findAllBySpecIdOrderByOrdersAsc(bean.getId());
            bean.setOptions(options);
        });
        return beans;
    }
}
