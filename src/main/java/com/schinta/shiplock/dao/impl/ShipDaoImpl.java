package com.schinta.shiplock.dao.impl;

import com.schinta.shiplock.dao.AbstractDao;
import com.schinta.shiplock.dao.ShipDao;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by wangyuannan on 2016/1/7.
 */
@Repository
public class ShipDaoImpl extends AbstractDao implements ShipDao {

    public ShipDaoImpl() {
        logger = LoggerFactory.getLogger(ShipDaoImpl.class);
    }

    @Override
    public List<Map<String, Object>> queryDftShipCategories() {
        String sql = "SELECT * FROM tb_ship WHERE default_value = 1";
        return getJdbcTemplate().queryForList(sql);
    }
}
