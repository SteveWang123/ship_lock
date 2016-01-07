package com.schinta.shiplock.service;

import com.schinta.shiplock.model.ShipLockCategory.ShipLockCategory;
import com.schinta.shiplock.model.ship.Ship;
import com.schinta.shiplock.model.shipLock.ShipLock;

import java.util.List;

/**
 * Created by wangyuannan on 2015/12/22.
 */
public interface Packer {

    public ShipLock doDownDimensionPacking(ShipLockCategory category, List<Ship> ships);

    public ShipLock doGreedyPacking(ShipLockCategory category, List<Ship> ships);

}
