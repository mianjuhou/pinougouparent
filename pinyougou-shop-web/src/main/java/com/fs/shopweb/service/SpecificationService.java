package com.fs.shopweb.service;

import com.fs.common.entity.PageResult;
import com.fs.shopweb.dao.SpecificationDao;
import com.fs.shopweb.dao.SpecificationOptionDao;
import com.fs.shopweb.pojo.Specification;
import com.fs.shopweb.pojo.Specification;
import com.fs.shopweb.pojo.SpecificationOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SpecificationService {

    @Autowired
    private SpecificationDao dao;

    @Autowired
    private SpecificationOptionDao optionDao;

    public void save(Specification bean) {
        dao.save(bean);
    }

    public void deleteById(long id) {
        dao.deleteById(id);
    }

    public void update(Specification bean) {
        dao.save(bean);
    }

    public List<Specification> findAll() {
        return dao.findAll();
    }

    public Specification findById(long id) {
        return dao.findById(id).get();
    }



    public PageResult<Specification> findPage(int pageSize, int pageNum, Specification bean) {
        Page<Specification> all = dao.findAll(getSpecification(bean), PageRequest.of(pageNum - 1, pageSize));
        return new PageResult<>(all.getTotalElements(), all.getContent());
    }


    public org.springframework.data.jpa.domain.Specification<Specification> getSpecification(Specification bean) {
        return (org.springframework.data.jpa.domain.Specification<Specification>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            if (!StringUtils.isEmpty(bean.getSpecName())) {
                Predicate predicate = criteriaBuilder.like(root.get("spec_name").as(String.class), "%" + bean.getSpecName() + "%");
                list.add(predicate);
            }
            Predicate[] predicates = new Predicate[list.size()];
            predicates = list.toArray(predicates);
            return criteriaBuilder.and(predicates);
        };
    }

    //其他
    public void saveAll(Specification specification) {
        Specification savedSpec = dao.save(specification);
        List<SpecificationOption> options = specification.getOptions();
        if (options != null) {
            options.forEach(option -> {
                option.setSpecId(savedSpec.getId());
                option.setId(null);
            });
            optionDao.saveAll(options);
        }
    }

    public void deleteAll(List<Long> ids) {
        ids.forEach(id -> {
            optionDao.deleteAllBySpecId(id);
            dao.deleteById(id);
        });
    }

    public void updateAll(Long id, Specification specification) {
        specification.setId(id);
        dao.save(specification);
        optionDao.deleteAllBySpecId(id);

        List<SpecificationOption> options = specification.getOptions();
        if (options != null) {
            options.forEach(option -> {
                option.setSpecId(id);
                option.setId(null);
            });
            optionDao.saveAll(specification.getOptions());
        }
    }


}
