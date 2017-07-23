package com.winit.robot.core;

import com.winit.robot.utils.IdWorker;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by stvli on 2015/11/10.
 */
public class RobotReturnValue extends BaseInfo implements Serializable {
    private Date refCreated;
    private Long refTraceNo;

    public Date getRefCreated() {
        return refCreated;
    }

    public void setRefCreated(Date refCreated) {
        this.refCreated = refCreated;
    }

    public Long getRefTraceNo() {
        return refTraceNo;
    }

    public void setRefTraceNo(Long refTraceNo) {
        this.refTraceNo = refTraceNo;
    }
}
