package com.schinta.shiplock.model.ShipLockCategory;

/**
 * Created by wangyuannan on 2015/12/22.
 */
public class ShipLockCategory {

    private int lockId;   //船闸类别ID
    private String lockName;//船闸名称
    private double width;//船闸可用宽度
    private double length;//船闸可用长度
    private double outerWidth;//船闸外围宽度
    private double outerLength;//船闸外围长度
    private int runtime;   //运行一次的时间（单位：分钟）
    private int totalCnt;  //一共运行的次数

    private int cnt;//船闸一天的运行次数(第几次运行)
    private double useRatio; //闸室利用率
    private int runtimeThre; //闸室运行的等待时间

    private boolean inwaterRestricted;  //是否有吃水限制
    private double inwater;  //吃水限制

    private boolean isCapacity; //是否有装载吨数的限制
    private double capacity;   //最大的装载吨数


    public boolean isCapacity() {
        return isCapacity;
    }

    public void setIsCapacity(boolean isCapacity) {
        this.isCapacity = isCapacity;
    }

    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }

    public double getInwater() {
        return inwater;
    }

    public void setInwater(double inwater) {
        this.inwater = inwater;
    }

    public boolean isInwaterRestricted() {
        return inwaterRestricted;
    }

    public void setInwaterRestricted(boolean inwaterRestricted) {
        this.inwaterRestricted = inwaterRestricted;
    }

    public int getLockId() {
        return lockId;
    }

    public void setLockId(int lockId) {
        this.lockId = lockId;
    }

    public String getLockName() {
        return lockName;
    }

    public void setLockName(String lockName) {
        this.lockName = lockName;
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

    public double getOuterWidth() {
        return outerWidth;
    }

    public void setOuterWidth(double outerWidth) {
        this.outerWidth = outerWidth;
    }

    public double getOuterLength() {
        return outerLength;
    }

    public void setOuterLength(double outerLength) {
        this.outerLength = outerLength;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public double getUseRatio() {
        return useRatio;
    }

    public void setUseRatio(double useRatio) {
        this.useRatio = useRatio;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public int getRuntimeThre() {
        return runtimeThre;
    }

    public void setRuntimeThre(int runtimeThre) {
        this.runtimeThre = runtimeThre;
    }

    public int getTotalCnt() {
        return totalCnt;
    }

    public void setTotalCnt(int totalCnt) {
        this.totalCnt = totalCnt;
    }
}
