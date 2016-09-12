package com.winit.cloudlink.benchmark;

import com.csvreader.CsvWriter;

import java.io.IOException;

/**
 * Created by stvli on 2015/11/20.
 */
public class ValueBeanWriter {
    private static ValueBeanWriter instance = new ValueBeanWriter();
    private int arraySize;
    private CsvWriter writer;

    public static ValueBeanWriter instance() {
        return ValueBeanWriter.instance;
    }

    private ValueBeanWriter() {

    }

    public void init(String filePath) {
        writer = new CsvWriter(filePath);
    }

    /**
     * 写入CSV文件
     */
    public void write(ValueBean valueBean) {
        try {
            writer.write(String.valueOf(valueBean.getId()));
            writer.write(String.valueOf(valueBean.getFromApp()));
            writer.write(String.valueOf(valueBean.getToApp()));
            writer.write(String.valueOf(valueBean.getContent()));
            writer.write(String.valueOf(valueBean.getSentTime()));
            writer.write(String.valueOf(valueBean.getReceivedTime()));
            writer.write(String.valueOf(valueBean.getElapsedTime()));
            writer.endRecord();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void destory() {
        if (writer != null) {
            writer.close();
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        destory();
    }
}
