package com.winit.cloudlink.message.internal.mapping;

import java.util.Map;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;

import com.google.common.collect.Maps;
import com.winit.cloudlink.common.utils.Assert;
import com.winit.cloudlink.config.Metadata;
import com.winit.cloudlink.message.ExchangeType;

/**
 * Created by stvli on 2016/6/4.
 */
public class MappingStrategyFactory {

    private static Map<ExchangeType, MappingStrategy> strategyMap = Maps.newHashMap();

    public static MappingStrategy buildMappingStrategy(Metadata metadata, ConnectionFactory connectionFactory,
                                                       ExchangeType exchangeType) {
        Assert.notNull(exchangeType, "'exchangeType' must can not be null.");

        MappingStrategy mappingStrategy = getMappingStrategy(exchangeType);

        if (null == mappingStrategy) {
            String lock = String.valueOf(exchangeType.name());
            synchronized (lock) {
                mappingStrategy = getMappingStrategy(exchangeType);
                if (null == mappingStrategy) {
                    mappingStrategy = createMappingStrategy(metadata, connectionFactory, exchangeType);
                }
            }
        }
        return mappingStrategy;
    }

    private static MappingStrategy createMappingStrategy(Metadata metadata, ConnectionFactory connectionFactory,
                                                         ExchangeType exchangeType) {
        MappingStrategy strategy = null;
        if (ExchangeType.Topic.equals(exchangeType)) {
            strategy = new TopicMappingStrategy();
        } else {
            strategy = new DirectMappingStrategy();
        }
        strategy.setMetadata(metadata);
        strategy.setConnectionFactory(connectionFactory);

        return strategy;
    }

    private static MappingStrategy getMappingStrategy(ExchangeType exchangeType) {
        if (ExchangeType.Topic.equals(exchangeType)) {
            return strategyMap.get(ExchangeType.Topic);
        } else {
            return strategyMap.get(ExchangeType.Direct);
        }
    }
}
