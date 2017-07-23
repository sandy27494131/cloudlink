package com.winit.cloudlink.message.importer.service.support;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.text.ParseException;
import java.util.Date;

import static com.winit.cloudlink.message.importer.utils.DateTimes.diffHours;

/**
 * Created by stvli on 2017/3/31.
 */
public class MessageFileFilter implements FileFilter {
    private static Logger logger = LoggerFactory.getLogger(MessageFileFilter.class);
    private static final FastDateFormat dateFormat = FastDateFormat.getInstance("yyyy-MM-dd-HH");
    public static final String DEFAULT_MESSAGE_FILE = "cloudlink-message.log";

    private Date start;

    public MessageFileFilter(Date start) {
        this.start = start;
    }

    @Override
    public boolean accept(File pathname) {
        if (pathname.isDirectory()) {
            return false;
        }
        String filename = pathname.getName();
        if (DEFAULT_MESSAGE_FILE.equals(filename)) {
            return true;
        }
        filename = filename.substring(0, filename.lastIndexOf("."));
        String[] filenameParts = StringUtils.split(filename, "_");
        if (filenameParts.length <= 1) {
            return false;
        }
        try {
            Date logDate = dateFormat.parse(filenameParts[1]);
            long hoursBetween = diffHours(logDate, start);
            return hoursBetween <= 0;
        } catch (ParseException e) {
            logger.error("读取日志文件名里的日期部分出错。", e);
        }
        return false;
    }
}
