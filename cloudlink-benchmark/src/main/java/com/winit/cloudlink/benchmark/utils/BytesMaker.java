package com.winit.cloudlink.benchmark.utils;

public class BytesMaker {
    public static byte[] make128() {
        return makeBytes(128);
    }

    public static byte[] make256() {
        return makeBytes(256);
    }

    public static byte[] make512() {
        return makeBytes(512);
    }

    public static byte[] make1k() {
        return makeBytes(1024);
    }

    public static byte[] make10k() {
        return makeBytes(10240);
    }

    public static byte[] makeBytes(int size) {
        byte[] bytes = new byte[size];
        for (int i = 0; i < size; i++) {
            bytes[i] = new Byte("1");
        }
        return bytes;
    }

    public static void main(String[] args) {
        String str = "A";
        System.out.println(str.getBytes().length);
    }

}
