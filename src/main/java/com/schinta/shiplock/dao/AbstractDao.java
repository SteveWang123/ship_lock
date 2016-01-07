package com.schinta.shiplock.dao;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;


/**
 * Created by Administrator on 2016/1/2.
 */
public class AbstractDao {

    protected static Logger logger;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    protected JdbcTemplate getJdbcTemplate(){
        return this.jdbcTemplate;
    }

}
