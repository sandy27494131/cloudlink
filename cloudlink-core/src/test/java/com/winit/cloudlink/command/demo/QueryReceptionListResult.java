package com.winit.cloudlink.command.demo;

import java.io.Serializable;
import java.util.Date;

public class QueryReceptionListResult implements Serializable {

    private String commandNo;         // 收货指令单号
    private String customerOrderNo;   // 客户订单号
    private String customerName;      // 客户名称
    private String customerCode;      // 客户编码
    private String pickupNo;          // 提货单号
    private String carPlateNumber;    // 车牌号信息
    private String pickupType;        // 提货方式
    private int    estimatePackageQty; // 计划包裹总数
    private float  estimateWeight;    // 下单重量
    private Float  actualWeight;      // 实际重量
    private Float  estimateVolume;    // 下单体积
    private Float  actualVolume;      // 实际体积
    private String status;            // 状态
    private Date   created;           // 创建时间

    public QueryReceptionListResult(){
        super();
    }

    public QueryReceptionListResult(String commandNo, String customerOrderNo, String customerName, String customerCode,
                                    String pickupNo, String carPlateNumber, String pickupType, int estimatePackageQty,
                                    float estimateWeight, Float actualWeight, Float estimateVolume, Float actualVolume,
                                    String status, Date created){
        super();
        this.commandNo = commandNo;
        this.customerOrderNo = customerOrderNo;
        this.customerName = customerName;
        this.customerCode = customerCode;
        this.pickupNo = pickupNo;
        this.carPlateNumber = carPlateNumber;
        this.pickupType = pickupType;
        this.estimatePackageQty = estimatePackageQty;
        this.estimateWeight = estimateWeight;
        this.actualWeight = actualWeight;
        this.estimateVolume = estimateVolume;
        this.actualVolume = actualVolume;
        this.status = status;
        this.created = created;
    }

    public String getCommandNo() {
        return commandNo;
    }

    public void setCommandNo(String commandNo) {
        this.commandNo = commandNo;
    }

    public String getCustomerOrderNo() {
        return customerOrderNo;
    }

    public void setCustomerOrderNo(String customerOrderNo) {
        this.customerOrderNo = customerOrderNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getPickupNo() {
        return pickupNo;
    }

    public void setPickupNo(String pickupNo) {
        this.pickupNo = pickupNo;
    }

    public String getCarPlateNumber() {
        return carPlateNumber;
    }

    public void setCarPlateNumber(String carPlateNumber) {
        this.carPlateNumber = carPlateNumber;
    }

    public String getPickupType() {
        return pickupType;
    }

    public void setPickupType(String pickupType) {
        this.pickupType = pickupType;
    }

    public int getEstimatePackageQty() {
        return estimatePackageQty;
    }

    public void setEstimatePackageQty(int estimatePackageQty) {
        this.estimatePackageQty = estimatePackageQty;
    }

    public float getEstimateWeight() {
        return estimateWeight;
    }

    public void setEstimateWeight(float estimateWeight) {
        this.estimateWeight = estimateWeight;
    }

    public Float getActualWeight() {
        return actualWeight;
    }

    public void setActualWeight(Float actualWeight) {
        this.actualWeight = actualWeight;
    }

    public Float getEstimateVolume() {
        return estimateVolume;
    }

    public void setEstimateVolume(Float estimateVolume) {
        this.estimateVolume = estimateVolume;
    }

    public Float getActualVolume() {
        return actualVolume;
    }

    public void setActualVolume(Float actualVolume) {
        this.actualVolume = actualVolume;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

}
