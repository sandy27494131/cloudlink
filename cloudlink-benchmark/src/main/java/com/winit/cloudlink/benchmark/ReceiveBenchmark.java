package com.winit.cloudlink.benchmark;

import com.winit.cloudlink.benchmark.utils.BytesMaker;
import com.winit.cloudlink.message.Message;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.concurrent.TimeUnit;


public class ReceiveBenchmark extends BaseBenchmark {
    private static Logger logger = LoggerFactory.getLogger(ReceiveBenchmark.class);
    private static final String csvFilePath = "receive-beanchmark.csv";

    private void start() {
        cloudlink.registerMessageHandler(new BenchmarkMessageHandler(getMessageType(),isMessageSaved()));
        File file = new File(csvFilePath);
        if (file.exists()) {
            file.delete();
        }
        ValueBeanWriter.instance().init(csvFilePath);
    }

    private void shutdown() {
        ValueBeanWriter.instance().destory();
    }

    public static void main(String[] args) throws RunnerException, InterruptedException {
        final ReceiveBenchmark benchmark = new ReceiveBenchmark();
        benchmark.start();
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                benchmark.shutdown();
            }
        });
        while (true) {
            Thread.sleep(10000);
        }
    }
}