package com.schinta.shiplock.model.shipLock;

import com.schinta.shiplock.model.ShipLockCategory.ShipLockCategory;
import com.schinta.shiplock.model.ship.Ship;

import java.util.List;

/**
 * Created by wangyuannan on 2015/12/22.
 */
/*闸次实体，用于表示实际运行的闸次*/
public class ShipLock {

    private int id;  //闸次的id（无实际意义）
    private ShipLockCategory category;  //船闸的类别
    private int cnt;   //船闸运行的闸次（第几次）
    private int unpacked; //该闸次运行后锚地的压船数量
    private String startTime;  //船闸这一次开始运行的时间（YYYY-MM-DD HH24：MI）
    private String endTime;   //船闸这一次运行结束的时间（YYYY-MM-DD HH24：MI）
    private double useRatio; //闸室的利用率
    private int weight;   //闸次的实际装载吨位
    List<Ship> ships;  //运了哪几条船过闸

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public ShipLockCategory getCategory() {
        return category;
    }

    public void setCategory(ShipLockCategory category) {
        this.category = category;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public List<Ship> getShips() {
        return ships;
    }

    public void setShips(List<Ship> ships) {
        this.ships = ships;
    }

    public double getUseRatio() {
        return useRatio;
    }

    public void setUseRatio(double useRatio) {
        this.useRatio = useRatio;
    }

    public int getUnpacked() {
        return unpacked;
    }

    public void setUnpacked(int unpacked) {
        this.unpacked = unpacked;
    }
}
