package com.fs.shopweb.service;

import com.fs.shopweb.dao.ItemDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ItemService {

    @Autowired
    private ItemDao dao;



}
