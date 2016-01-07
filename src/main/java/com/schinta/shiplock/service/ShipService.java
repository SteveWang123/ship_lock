package com.schinta.shiplock.service;

import java.util.List;
import java.util.Map;

/**
 * Created by wangyuannan on 2016/1/7.
 */
public interface ShipService {

    /*获取所有系统默认的船型*/
    public List<Map<String, Object>> getShipCategories();
}
