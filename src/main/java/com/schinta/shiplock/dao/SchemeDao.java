package com.schinta.shiplock.dao;

import com.schinta.shiplock.model.Scheme.Scheme;
import com.schinta.shiplock.model.shipLock.ShipLock;

import java.util.List;
import java.util.Map;

/**
 * Created by wangyuannan on 2015/12/31.
 */
public interface SchemeDao {

    /*保存方案，并返回该方案的id*/
    public long insertScheme(Scheme scheme);

    /*保存方案的艘次按月占比*/
    public void insertPmonth(long schemeId, Scheme scheme);

    /*保存方案计算结果的闸次信息和船闸基本信息*/
    public int[] insertShipLock(long schemeId, List<ShipLock> shipLocks);

    /*保存每次船闸装的船*/
    public void insertShip(long schemId, List<Map<String, Object>> shipLockIds, List<ShipLock> shipLocks);

    /*根据方案id， 找到这个方案的所有闸次的id集合*/
    public List<Map<String, Object>> queryShipLockIdsByScheme(int schemeId);

    /*查找所有方案*/
    public List<Map<String, Object>> querySchemeList();

    /*获取系统默认的艘次按月占比*/
    public List<Map<String, Object>> queryDftPmonth();
}
