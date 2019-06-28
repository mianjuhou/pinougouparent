package com.fs.shopweb.service;

import com.fs.common.entity.PageResult;
import com.fs.shopweb.dao.SellerDao;
import com.fs.shopweb.pojo.Seller;
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
public class SellerService {
    @Autowired
    private SellerDao dao;

    /**
     * 增
     */
    public void save(Seller bean) {
        dao.save(bean);
    }

    /**
     * 删
     */
    public void deleteById(String id) {
        dao.deleteById(id);
    }

    /**
     * 改
     */
    public void update(Seller bean) {
        dao.save(bean);
    }

    /**
     * 查
     */
    //全部查
    public List<Seller> findAll() {
        return dao.findAll();
    }

    //ID查
    public Seller findById(String id) {
        return dao.findById(id).get();
    }

    //条件查
    public List<Seller> findSearch(Seller bean) {
        return dao.findAll(getSpecification(bean));
    }

    //分页查
    public PageResult<Seller> findPage(int pageSize, int pageNum, Seller bean) {
        Page<Seller> page = dao.findAll(getSpecification(bean), PageRequest.of(pageNum - 1, pageSize));
        return new PageResult<>(page.getTotalElements(), page.getContent());
    }

    //组装查询条件
    public Specification<Seller> getSpecification(Seller bean) {
        return (Specification<Seller>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            if (!StringUtils.isEmpty(bean.getName())) {
                Predicate predicate = criteriaBuilder.like(root.get("name").as(String.class), "%" + bean.getName() + "%");
                list.add(predicate);
            }
            if (!StringUtils.isEmpty(bean.getNickName())) {
                Predicate predicate = criteriaBuilder.like(root.get("nickName").as(String.class), "%" + bean.getNickName() + "%");
                list.add(predicate);
            }
            if (!StringUtils.isEmpty(bean.getStatus())) {
                Predicate predicate = criteriaBuilder.equal(root.get("status").as(String.class), bean.getStatus());
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
    public void updateStatus(String id, String status) {
        Seller bean = new Seller();
        bean.setSellerId(id);
        bean.setStatus(status);
        dao.save(bean);
    }



}
