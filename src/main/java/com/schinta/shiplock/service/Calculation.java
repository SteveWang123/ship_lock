package com.schinta.shiplock.service;

import com.schinta.shiplock.model.Scheme.Scheme;
import com.schinta.shiplock.model.ShipLockCategory.ShipLockCategory;
import com.schinta.shiplock.model.ship.Ship;
import com.schinta.shiplock.model.shipLock.ShipLock;

import java.util.List;

/**
 * Created by wangyuannan on 2015/12/17.
 */
public interface Calculation {

    public Integer doCalculation4N(Scheme scheme);  //计算的第一步：根据货运总量计算出一年需要的最少总船数

    public List<Ship> doCalculation4ShipSeries(Scheme scheme, Integer N);  //计算的第二步：根据总船数和方案信息，计算来船序列

    public List<ShipLock> doCalculation4Packing(Scheme scheme, List<Ship> sequence, List<ShipLockCategory> shipLockCategories);
}
