package com.fs.content.service;

import com.fs.common.entity.PageResult;
import com.fs.content.dao.ContentCategoryDao;
import com.fs.content.pojo.ContentCategory;
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
public class ContentCategoryService {

    @Autowired
    private ContentCategoryDao categoryDao;

    /**
     * 增
     */
    public void save(ContentCategory bean) {
        categoryDao.save(bean);
    }

    /**
     * 删
     */
    public void deleteById(Long id) {
        categoryDao.deleteById(id);
    }

    /**
     * 改
     */
    public void update(ContentCategory bean) {
        categoryDao.save(bean);
    }

    /**
     * 查
     */
    /*ID查
     */
    public ContentCategory findById(Long id) {
        return categoryDao.findById(id).get();
    }

    /*全部查
     */
    public List<ContentCategory> findAll() {
        return categoryDao.findAll();
    }

    /*条件查
     */
    public List<ContentCategory> findSearch(ContentCategory bean) {
        return categoryDao.findAll(getSpecification(bean));
    }

    /*分页查
     */
    public PageResult<ContentCategory> findPage(int pageSize, int pageNum, ContentCategory bean) {
        Page<ContentCategory> page = categoryDao.findAll(getSpecification(bean), PageRequest.of(pageNum - 1, pageSize));
        return new PageResult<>(page.getTotalElements(), page.getContent());
    }

    /*组装查询条件
     */
    public Specification<ContentCategory> getSpecification(ContentCategory bean) {
        return (Specification<ContentCategory>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            //name模糊查询
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
    /*批量删除
     */
    public void deleteByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return;
        }
        categoryDao.deleteByIds(ids);
    }

}
