package com.winit.cloudlink.benchmark;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winit.cloudlink.benchmark.utils.StringMaker;
import com.winit.cloudlink.message.Message;

@State(Scope.Thread)
public class SendBenchmark extends BaseBenchmark {

    private static Logger        logger = LoggerFactory.getLogger(SendBenchmark.class);
    private static AtomicInteger index  = new AtomicInteger(1);

    @Benchmark
    public void send() {
        ValueBean value = new ValueBean();
        value.setId(index.getAndIncrement());
        value.setFromApp(getAppId());
        value.setToApp(getToAppId());
        String content = StringMaker.makeStrings(getMessageSize());
        value.setContent(content);
        value.setSentTime(new Date().getTime());
        Message message = cloudlink.newMessageBuilder().direct(getToAppId(), getMessageType(), value).build();
        cloudlink.sendMessage(message);
    }

    public static void main(String[] args) throws RunnerException {
        SendBenchmark benchmark = new SendBenchmark();
        String threads = benchmark.getProperties().getProperty("threads");
        int threadNum = 1;
        try {
            threadNum = Integer.parseInt(threads);
        } catch (Exception e) {
            logger.warn("threads must be Integer.", e);
        }

        Options opt = new OptionsBuilder().include(SendBenchmark.class.getSimpleName())
            .warmupIterations(0)
            .measurementIterations(1)
            .mode(Mode.Throughput)
            // 计算一个时间单位内操作数量
            .measurementTime(TimeValue.seconds(benchmark.getSendSecondes()))
            // 运行时间
            .forks(1)
            .threads(threadNum)
            .build();
        new Runner(opt).run();
    }
}
