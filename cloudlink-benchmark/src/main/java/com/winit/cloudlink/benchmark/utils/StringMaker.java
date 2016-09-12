package com.winit.cloudlink.benchmark.utils;

import org.apache.commons.lang3.StringUtils;

public class StringMaker {
    public static String make128() {
        return makeStrings(128);
    }

    public static String make256() {
        return makeStrings(256);
    }

    public static String make512() {
        return makeStrings(512);
    }

    public static String make1k() {
        return makeStrings(1024);
    }

    public static String make10k() {
        return makeStrings(10240);
    }

    public static String makeStrings(int size) {
        return StringUtils.repeat("1", size);
    }
}
