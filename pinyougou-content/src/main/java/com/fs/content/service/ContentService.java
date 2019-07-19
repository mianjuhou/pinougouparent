package com.fs.content.service;

import com.fs.common.entity.PageResult;
import com.fs.content.dao.ContentDao;
import com.fs.content.pojo.Content;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ContentService {

    @Autowired
    private ContentDao contentDao;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 增
     */
    public void save(Content bean) {
        contentDao.save(bean);
        redisTemplate.boundHashOps("content").delete(bean.getCategoryId());
    }

    /**
     * 删
     */
    public void deleteById(Long id) {
        Long categoryId = findById(id).getCategoryId();
        contentDao.deleteById(id);
        redisTemplate.boundHashOps("content").delete(categoryId);
    }

    /**
     * 改
     */
    public void update(Content bean) {
        Long oldCategoryId = findById(bean.getId()).getCategoryId();
        redisTemplate.boundHashOps("content").delete(oldCategoryId);

        contentDao.save(bean);
        if (oldCategoryId.longValue() != bean.getCategoryId().longValue()) {
            redisTemplate.boundHashOps("content").delete(bean.getCategoryId());
        }
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
        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.ASC, "categoryId"));
        orders.add(new Sort.Order(Sort.Direction.ASC, "sortOrder"));
        return contentDao.findAll(getSpecification(bean), Sort.by(orders));
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
                Predicate predicate = criteriaBuilder.equal(root.get("categoryId").as(String.class), bean.getCategoryId());
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

    /*根据分类ID查询
     */
    public List<Content> findByCategoryId(Long categoryId) {
        List<Content> list = (List<Content>) redisTemplate.boundHashOps("content").get(categoryId);
        if (list == null || list.isEmpty()) {
            System.out.println("从数据库中获取数据");
            Content content = new Content();
            content.setCategoryId(categoryId);
            content.setStatus("1");
            list = findSearch(content);
            redisTemplate.boundHashOps("content").put(categoryId, list);
        } else {
            System.out.println("从缓存中获取数据");
        }
        return list;
    }

}
