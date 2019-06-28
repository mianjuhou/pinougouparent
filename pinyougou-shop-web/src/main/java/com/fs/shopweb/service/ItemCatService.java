package com.fs.shopweb.service;

import com.fs.common.entity.PageResult;
import com.fs.shopweb.dao.ItemCatDao;
import com.fs.shopweb.pojo.ItemCat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ItemCatService {

    @Autowired
    private ItemCatDao dao;

    /**
     * 增
     */
    public void save(ItemCat bean) {
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
    public void update(ItemCat bean) {
        dao.save(bean);
    }

    /**
     * 查
     */
    //ID查
    public ItemCat findById(long id) {
        return dao.findById(id).get();
    }

    //全部查
    public List<ItemCat> findAll() {
        return dao.findAll();
    }

    //条件查
    public List<ItemCat> findSearch(ItemCat bean) {
        return dao.findAll(getSpecification(bean));
    }

    //分页查
    public PageResult<ItemCat> findPage(int pageSize, int pageNum, ItemCat bean) {
        Page<ItemCat> page = dao.findAll(getSpecification(bean), PageRequest.of(pageNum - 1, pageSize));
        return new PageResult<>(page.getTotalElements(), page.getContent());
    }

    //查询条件
    public Specification<ItemCat> getSpecification(ItemCat bean) {
        return (Specification<ItemCat>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            //name字段模糊匹配
            if (!StringUtils.isEmpty(bean.getName())) {
                Predicate predicate = criteriaBuilder.like(root.get("name").as(String.class), "%" + bean.getName() + "%");
                list.add(predicate);
            }
            //parent_id字段精确匹配
            if (!StringUtils.isEmpty(bean.getParentId())) {
                Predicate predicate = criteriaBuilder.equal(root.get("parentId").as(String.class), bean.getParentId());
                list.add(predicate);
            }
            //type_id字段精确匹配
            if (!StringUtils.isEmpty(bean.getTypeId())) {
                Predicate predicate = criteriaBuilder.equal(root.get("typeId").as(String.class), bean.getTypeId());
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
    /*多ID级联删除
     */
    public void deleteCascadeByIds(List<Long> ids) {
        deleteCascade(ids);
    }

    /*递归删除函数
     */
    public void deleteCascade(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return;
        } else {
            ids.forEach(id -> {
                List<BigInteger> subIds = dao.findIdByParentId(id);
                List<Long> subLids = new ArrayList<>();
                subIds.forEach(subId->{
                    subLids.add(Long.parseLong(subId.toString()));
                });
                deleteCascade(subLids);
            });
        }
        dao.deleteByIds(ids);
    }

}
