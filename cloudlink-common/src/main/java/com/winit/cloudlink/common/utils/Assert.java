package com.winit.cloudlink.common.utils;

/**
 * @author <a href="mailto:gang.lvg@alibaba-inc.com">kimi</a>
 */
public abstract class Assert {

    protected Assert(){
    }

    public static void notNull(Object obj, String message) {
        if (obj == null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notBlank(String str, String message) {
        if (str == null || str.trim().length() == 0) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notBlank(String[] str, String message) {
        if (str == null || str.length == 0) {
            throw new IllegalArgumentException(message);
        }

        boolean blank = true;
        for (int i = 0; i < str.length; i++) {
            if (!StringUtils.isBlank(str[i])) {
                blank = false;
            }
        }
        if (blank) {
            throw new IllegalArgumentException(message);
        }
    }

}
