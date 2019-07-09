package com.fs.content.service;

import com.fs.common.entity.PageResult;
import com.fs.content.dao.ContentDao;
import com.fs.content.pojo.Content;
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
public class ContentService {

    @Autowired
    private ContentDao contentDao;

    /**
     * 增
     */
    public void save(Content bean) {
        contentDao.save(bean);
    }

    /**
     * 删
     */
    public void deleteById(Long id) {
        contentDao.deleteById(id);
    }

    /**
     * 改
     */
    public void update(Content bean) {
        contentDao.save(bean);
    }

    /**
     * 查
     */
    /*ID查
     */
    public Content findById(Long id) {
        return contentDao.findById(id).get();
    }

    /*全部查
     */
    public List<Content> findAll() {
        return contentDao.findAll();
    }

    /*条件查
     */
    public List<Content> findSearch(Content bean) {
        return contentDao.findAll(getSpecification(bean));
    }

    /*分页查
     */
    public PageResult<Content> findPage(int pageSize, int pageNum, Content bean) {
        Page<Content> page = contentDao.findAll(getSpecification(bean), PageRequest.of(pageNum - 1, pageSize));
        return new PageResult<>(page.getTotalElements(), page.getContent());
    }

    /*组装查询条件
     */
    public Specification<Content> getSpecification(Content bean) {
        return (Specification<Content>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            //categoryId精确匹配
            if (!StringUtils.isEmpty(bean.getCategoryId())) {
                Predicate predicate = criteriaBuilder.equal(root.get("category_id").as(String.class), bean.getCategoryId());
                list.add(predicate);
            }

            //title模糊匹配
            if (!StringUtils.isEmpty(bean.getTitle())) {
                Predicate predicate = criteriaBuilder.like(root.get("title").as(String.class), "%" + bean.getTitle() + "%");
                list.add(predicate);
            }
            //status精确匹配
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
    /*批量查询
     */
    public List<Content> findByIds(List<Long> ids) {
        return contentDao.findAllById(ids);
    }

    /*批量删除
     */
    public void deleteByIds(List<Long> ids) {
        contentDao.deleteByIds(ids);
    }

    /*批量修改状态
     */
    public void updateStatusByIds(List<Long> ids, String status) {
        contentDao.updateStatusByIds(ids, status);
    }

}
