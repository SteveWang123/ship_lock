package com.schinta.shiplock.model.Scheme;

import com.schinta.shiplock.model.ShipLockCategory.ShipLockCategory;
import com.schinta.shiplock.model.shipCategory.ShipCategory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by wangyuannan on 2015/12/22.
 */
public class Scheme {

    private int id;
    private String name;
    private String createTime;
    private String year;
    private Double total;
    private String startDay;  //方案计算的开始日期（yyyy-MM-DD）
    private String endDay;    //方案计算的结束日期
    private int unpackedCnt;  //没有装载的船的个数
    private double scheduleWeight_ratio; //闸室利用率的权重比例（0 ~ 1）

    private ArrayList<ShipCategory> shipCategory;
    private ArrayList<ShipLockCategory> shipLockCategory;
    private HashMap<String, Double> pByMonth;  //{"2020-01":0.09, "2020-02":0.087,...}

    public ArrayList<ShipLockCategory> getShipLockCategory() {
        return shipLockCategory;
    }

    public void setShipLockCategory(ArrayList<ShipLockCategory> shipLockCategory) {
        this.shipLockCategory = shipLockCategory;
    }

    public double getScheduleWeight_ratio() {
        return scheduleWeight_ratio;
    }

    public void setScheduleWeight_ratio(double scheduleWeight_ratio) {
        this.scheduleWeight_ratio = scheduleWeight_ratio;
    }

    public int getId() {
        return id;
    }

    public int getUnpackedCnt() {
        return unpackedCnt;
    }

    public void setUnpackedCnt(int unpackedCnt) {
        this.unpackedCnt = unpackedCnt;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public ArrayList<ShipCategory> getShipCategory() {
        return shipCategory;
    }

    public void setShipCategory(ArrayList<ShipCategory> shipCategory) {
        this.shipCategory = shipCategory;
    }

    public HashMap<String, Double> getpByMonth() {
        return pByMonth;
    }

    public void setpByMonth(HashMap<String, Double> pByMonth) {
        this.pByMonth = pByMonth;
    }

    public String getStartDay() {
        return startDay;
    }

    public void setStartDay(String startDay) {
        this.startDay = startDay;
    }

    public String getEndDay() {
        return endDay;
    }

    public void setEndDay(String endDay) {
        this.endDay = endDay;
    }
}
