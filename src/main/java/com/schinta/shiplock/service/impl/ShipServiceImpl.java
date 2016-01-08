package com.schinta.shiplock.service.impl;

import com.schinta.shiplock.dao.ShipDao;
import com.schinta.shiplock.service.ShipService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by wangyuannan on 2016/1/7.
 */
@Service
public class ShipServiceImpl   implements ShipService {

    static public Logger logger = LoggerFactory.getLogger(ShipServiceImpl.class);

    @Autowired
    ShipDao shipDao;

    @Transactional(readOnly = true)
    @Override
    public List<Map<String, Object>> getDftShipCategories() {
        return shipDao.queryDftShipCategories();
    }
}
