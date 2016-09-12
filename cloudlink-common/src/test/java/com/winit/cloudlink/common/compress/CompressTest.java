package com.winit.cloudlink.common.compress;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.winit.cloudlink.common.exception.CompressionException;

public class CompressTest {

    private static Compress gzibCompress = new GZipCompress();

    private static Compress zlibCompress = new ZLibCompress();

    @Test
    public void test1() {
        String data = "{\"@type\":\"com.winit.oms.wh.systemorder.orderdistribution.message.CustomerOrderMessage\",\"customerOrderType\":{\"code\":\"WarehouseInbound\",\"description\":\"海外仓入库单\"},\"orderNo\":\"OW16000000002030CN\"}";
        System.out.println(data);

        try {
            byte[] val = zlibCompress.compress(data.getBytes("UTF-8"));

            byte[] nData = zlibCompress.decompress(val);
            System.out.println(new String(nData, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (CompressionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void test() {
        int size = 1024;
        byte[] data1024 = new byte[size];
        for (int i = 0; i < size; i++) {
            data1024[i] = 1;
        }

        byte[] newData = null;
        long startTime = System.nanoTime();
        try {
            newData = zlibCompress.compress(data1024);
        } catch (CompressionException e) {
            e.printStackTrace();
        }
        long usedTime = System.nanoTime() - startTime;
        System.out.println("zlib   usedTime: " + usedTime);
        System.out.println("zlib old length: " + size);
        System.out.println("zlib new length: " + newData.length);

        startTime = System.nanoTime();
        try {
            newData = gzibCompress.compress(data1024);
        } catch (CompressionException e) {
            e.printStackTrace();
        }
        usedTime = System.nanoTime() - startTime;
        System.out.println("------------------------------");
        System.out.println("gzib   usedTime: " + usedTime);
        System.out.println("gzib old length: " + size);
        System.out.println("gzib new length: " + newData.length);
    }

    @Test
    public void testZLibCompressCPU() {
        // Compress compress = new GZipCompress();
        /*
         * Compress compress = new ZLibCompress(); List<Thread> threads = new
         * ArrayList<Thread>(); int threadNum = 20; int runNum = 10; int dataLen
         * = 1024 * 1000; CompressThread thread; for (int i = 0; i < threadNum;
         * i++) { thread = new CompressThread(compress, runNum, dataLen);
         * threads.add(thread); } for (int i = 0; i < threads.size(); i++) {
         * threads.get(i).start(); } for (int i = 0; i < threads.size(); i++) {
         * try { threads.get(i).join(); } catch (InterruptedException e) {
         * e.printStackTrace(); } }
         */

    }

    class CompressThread extends Thread {

        private Compress compress;

        private int      runNum;

        private int      dataLen;

        public CompressThread(Compress compress, int runNum, int dataLen){
            this.compress = compress;
            this.runNum = runNum;
            this.dataLen = dataLen;
        }

        @Override
        public void run() {
            byte[] data = new byte[dataLen];

            for (int i = 0; i < dataLen; i++) {
                data[i] = 1;
            }

            while (runNum-- > 0) {
                try {
                    compress.compress(data);
                    System.out.println("run num: " + runNum);
                    Thread.currentThread().sleep(100);
                } catch (CompressionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }

    }

}
