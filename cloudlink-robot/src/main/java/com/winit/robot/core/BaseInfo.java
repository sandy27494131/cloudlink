package com.winit.robot.core;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by stvli on 2017/3/28.
 */
public class BaseInfo implements Serializable {
    private Long traceNo;
    private Date created = new Date();

    public Long getTraceNo() {
        return traceNo;
    }

    public void setTraceNo(Long traceNo) {
        this.traceNo = traceNo;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
