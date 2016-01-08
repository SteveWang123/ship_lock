package com.schinta.shiplock.service;

import com.schinta.shiplock.model.Scheme.Scheme;
import com.schinta.shiplock.model.shipLock.ShipLock;

import java.util.List;
import java.util.Map;

/**
 * Created by wangyuannan on 2016/1/4.
 */
public interface SchemeService {

    /*保存方案的基本信息，输入条件和计算结果*/
    public void saveScheme(Scheme scheme, List<ShipLock> shipLocks);

    /*获取方案列表
    * @param  cnt 查找的方案的最大个数
    * */
    public List<Scheme> getSchemes(int cnt);

    /*获取系统内的默认的艘次按月占比*/
    public List<Map<String, Object>> getDftPmonth();


}
