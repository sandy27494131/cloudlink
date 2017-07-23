package com.winit.cloudlink.message.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * 异常信息工具类
 * 
 * @author jianke.zhang 2015-4-16 下午2:44:09
 * @since 1.0
 */
public class ExceptionUtils {

    public static String getStackTrace(Throwable t) {
        final Writer result = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(result);
        t.printStackTrace(printWriter);
        return result.toString();
    }
}
