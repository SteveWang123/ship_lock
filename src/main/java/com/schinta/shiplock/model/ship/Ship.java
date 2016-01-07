package com.schinta.shiplock.model.ship;

import com.schinta.shiplock.model.shipCategory.ShipCategory;
import com.schinta.shiplock.model.shipLock.ShipLock;

/**
 * Created by wangyuannan on 2015/12/22.
 */
public class Ship {

    private int shipId;  //船的id
    private ShipCategory shipcategory;  //船舶的类型
    private double width;  //宽度
    private double length;  //长度
    private double maxInWater;  //最大吃水深度
    private double capacity;   //吨位
    private double load;   //这个船的负载系数
    private int maxWaitTime;   //最大等待时间

    private double x;    //过闸时停泊位置的x坐标
    private double y;   //过闸时停泊位置的y坐标
    private boolean isMoved; //是否已经过闸

    private ShipLock shipLock; //过得哪一个闸

    private String arrivalDatetime;  //到锚地时间 YYYY-MM-DD HH24：MI
    private String departDatetime;  //离开锚地时间 YYYY-MM-DD HH24：MI，即闸室运行开始时间
    private int waitTime;           //等待时间，以分钟表示

    public Ship(int shipId, ShipCategory shipcategory, double width, double length, double maxInWater, double capacity, double load, int maxWaitTime, boolean isMoved) {
        this.shipId = shipId;
        this.shipcategory = shipcategory;
        this.width = width;
        this.length = length;
        this.maxInWater = maxInWater;
        this.capacity = capacity;
        this.load = load;
        this.maxWaitTime = maxWaitTime;
        this.isMoved = isMoved;
    }

    public int getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getMaxInWater() {
        return maxInWater;
    }

    public void setMaxInWater(double maxInWater) {
        this.maxInWater = maxInWater;
    }

    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }

    public double getLoad() {
        return load;
    }

    public void setLoad(double load) {
        this.load = load;
    }

    public int getMaxWaitTime() {
        return maxWaitTime;
    }

    public void setMaxWaitTime(int maxWaitTime) {
        this.maxWaitTime = maxWaitTime;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getShipId() {
        return shipId;
    }

    public void setShipId(int shipId) {
        this.shipId = shipId;
    }

    public boolean isMoved() {
        return isMoved;
    }

    public void setIsMoved(boolean isMoved) {
        this.isMoved = isMoved;
    }

    public ShipLock getShipLock() {
        return shipLock;
    }

    public void setShipLock(ShipLock shipLock) {
        this.shipLock = shipLock;
    }

    public String getArrivalDatetime() {
        return arrivalDatetime;
    }

    public void setArrivalDatetime(String arrivalDatetime) {
        this.arrivalDatetime = arrivalDatetime;
    }

    public String getDepartDatetime() {
        return departDatetime;
    }

    public void setDepartDatetime(String departDatetime) {
        this.departDatetime = departDatetime;
    }

    public ShipCategory getShipcategory() {
        return shipcategory;
    }

    public void setShipcategory(ShipCategory shipcategory) {
        this.shipcategory = shipcategory;
    }

    @Override
    public String toString(){
        return this.shipcategory + " , 船id： " + this.shipId + ", 模拟到达时间： " + this.arrivalDatetime + ", 长：" + this.length + ", 宽：" + this.width;
    }

}
