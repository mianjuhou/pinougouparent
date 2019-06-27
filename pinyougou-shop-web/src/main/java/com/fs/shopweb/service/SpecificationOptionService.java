package com.fs.shopweb.service;

import com.fs.shopweb.dao.SpecificationOptionDao;
import com.fs.shopweb.pojo.SpecificationOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecificationOptionService {

    @Autowired
    private SpecificationOptionDao dao;

    public List<SpecificationOption> findBySpecId(long specId){
        return dao.findAllBySpecIdOrderByOrdersAsc(specId);
    }

    public void deleteBySpecId(long specId){
        dao.deleteAllBySpecId(specId);
    }
}
