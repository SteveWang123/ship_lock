package com.schinta.shiplock.model.shipCategory;

/**
 * Created by wangyuannan on 2015/12/21.
 */
public class ShipCategory {

    private String categoryId;   //船类别ID
    private String categoryName;   //船类别的名称
    private double width;  //该船型宽度
    private double length;//该船型长度
    private double maxInWater;//该船型最大吃水深度
    private double capacity;//该船型吨位
    private double designLoad;//该船型设计的负载系数
    private double fluctuation;//该船型给定的负载系数波动系数
    private double ratio;//该船型每年的占比
    private int maxWaitTime;//该船型最大等待时间，以分钟表示

    public ShipCategory(String categoryId, String categoryName, double width, double length, double maxInWater, double designLoad, double capacity, double fluctuation, int maxWaitTime) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.width = width;
        this.length = length;
        this.maxInWater = maxInWater;
        this.capacity = capacity;
        this.designLoad = designLoad;
        this.fluctuation = fluctuation;
        this.maxWaitTime = maxWaitTime;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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

    public double getDesignLoad() {
        return designLoad;
    }

    public void setDesignLoad(double designLoad) {
        this.designLoad = designLoad;
    }

    public double getFluctuation() {
        return fluctuation;
    }

    public void setFluctuation(double fluctuation) {
        this.fluctuation = fluctuation;
    }

    public double getRatio() {
        return ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }

    public int getMaxWaitTime() {
        return maxWaitTime;
    }

    public void setMaxWaitTime(int maxWaitTime) {
        this.maxWaitTime = maxWaitTime;
    }

    @Override
    public String toString(){
        return "船类型： " + this.categoryName;
    }
}
