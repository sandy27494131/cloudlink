package com.winit.robot.utils;

import org.apache.commons.lang3.time.FastDateFormat;

import java.util.Date;

/**
 * Created by stvli on 2017/3/28.
 */
public class DateTimes {
    private static final FastDateFormat dateFormat = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");

    /**
     * 时间差（以秒为单位）
     *
     * @param start
     * @param end
     * @return
     */
    public static final long diffSeconds(Date start, Date end) {
        return diffMillis(start, end) / 1000;
    }

    /**
     * 时间差（以毫秒为单位）
     *
     * @param start
     * @param end
     * @return
     */
    public static final long diffMillis(Date start, Date end) {
        return end.getTime() - start.getTime();
    }

    public static final String format(Date date) {
        return dateFormat.format(date);
    }

    public static final String format(long timestamp) {
        return dateFormat.format(timestamp);
    }
}
