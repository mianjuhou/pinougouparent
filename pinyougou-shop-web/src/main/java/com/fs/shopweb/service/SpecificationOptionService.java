package com.fs.shopweb.service;

import com.fs.common.entity.PageResult;
import com.fs.shopweb.dao.SpecificationOptionDao;
import com.fs.shopweb.pojo.SpecificationOption;
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
public class SpecificationOptionService {

    @Autowired
    private SpecificationOptionDao dao;

    /**
     * 增
     */
    public void save(SpecificationOption bean) {
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
    public void update(SpecificationOption bean) {
        dao.save(bean);
    }

    /**
     * 查
     */
    //ID查
    public SpecificationOption findById(long id) {
        return dao.findById(id).get();
    }

    //全部查
    public List<SpecificationOption> findAll() {
        return dao.findAll();
    }

    //条件查
    public List<SpecificationOption> findSearch(SpecificationOption bean) {
        return dao.findAll(getSpecification(bean));
    }

    //分页查
    public PageResult<SpecificationOption> findPage(int pageSize, int pageNum, SpecificationOption bean) {
        Page<SpecificationOption> page = dao.findAll(getSpecification(bean), PageRequest.of(pageNum - 1, pageSize));
        return new PageResult<>(page.getTotalElements(), page.getContent());
    }

    //查询条件
    public Specification<SpecificationOption> getSpecification(SpecificationOption bean) {
        return (Specification<SpecificationOption>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            //option_name字段模糊匹配
            if (!StringUtils.isEmpty(bean.getOptionName())) {
                Predicate predicate = criteriaBuilder.like(root.get("optionName").as(String.class), "%" + bean.getOptionName() + "%");
                list.add(predicate);
            }
            //spec_id字段精确匹配
            if (!StringUtils.isEmpty(bean.getSpecId())) {
                Predicate predicate = criteriaBuilder.equal(root.get("specId").as(String.class), bean.getSpecId());
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
    //
    public List<SpecificationOption> findBySpecId(long specId) {
        //方案一
//        SpecificationOption option = new SpecificationOption();
//        option.setSpecId(specId);
//        return findSearch(option);
        //方案二
        return dao.findAllBySpecIdOrderByOrdersAsc(specId);
    }

    //
    public void deleteBySpecId(long specId) {
        dao.deleteAllBySpecId(specId);
    }
}
