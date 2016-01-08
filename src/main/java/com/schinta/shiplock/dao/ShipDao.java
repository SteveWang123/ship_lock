package com.schinta.shiplock.dao;


import java.util.List;
import java.util.Map;

/**
 * Created by wangyuannan on 2016/1/7.
 */
public interface ShipDao {

    /*获取系统默认的船型数据*/
    public List<Map<String, Object>> queryDftShipCategories();
}
