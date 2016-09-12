package com.winit.cloudlink.command.internal.command;

import java.io.Serializable;

public class QueryReceptionListParam implements Serializable {

    private String commandNo;      // 收货指令单号（可选）
    private String pickupNo;       // 提货单号（可选）
    private String customerOrderNo; // 订单号（可选）
    private String carPlateNumber; // 车牌号（可选）
    private String customerCode;   // 客户编码（可选）
    private String pickupType;     // 提货方式（可选）
    private String status;         // 状态（可选）
    private long   startCreateTime; // 创建时间-开始（可选）
    private long   endCreateTime;  // 创建时间-结束（可选）

    public QueryReceptionListParam(){
        super();
    }

    public QueryReceptionListParam(String commandNo, String pickupNo, String customerOrderNo, String carPlateNumber,
                                   String customerCode, String pickupType, String status, long startCreateTime,
                                   long endCreateTime){
        super();
        this.commandNo = commandNo;
        this.pickupNo = pickupNo;
        this.customerOrderNo = customerOrderNo;
        this.carPlateNumber = carPlateNumber;
        this.customerCode = customerCode;
        this.pickupType = pickupType;
        this.status = status;
        this.startCreateTime = startCreateTime;
        this.endCreateTime = endCreateTime;
    }

    public String getCommandNo() {
        return commandNo;
    }

    public void setCommandNo(String commandNo) {
        this.commandNo = commandNo;
    }

    public String getPickupNo() {
        return pickupNo;
    }

    public void setPickupNo(String pickupNo) {
        this.pickupNo = pickupNo;
    }

    public String getCustomerOrderNo() {
        return customerOrderNo;
    }

    public void setCustomerOrderNo(String customerOrderNo) {
        this.customerOrderNo = customerOrderNo;
    }

    public String getCarPlateNumber() {
        return carPlateNumber;
    }

    public void setCarPlateNumber(String carPlateNumber) {
        this.carPlateNumber = carPlateNumber;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getPickupType() {
        return pickupType;
    }

    public void setPickupType(String pickupType) {
        this.pickupType = pickupType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getStartCreateTime() {
        return startCreateTime;
    }

    public void setStartCreateTime(long startCreateTime) {
        this.startCreateTime = startCreateTime;
    }

    public long getEndCreateTime() {
        return endCreateTime;
    }

    public void setEndCreateTime(long endCreateTime) {
        this.endCreateTime = endCreateTime;
    }

}
