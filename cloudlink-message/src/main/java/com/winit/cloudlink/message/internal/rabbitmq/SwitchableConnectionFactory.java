package com.winit.cloudlink.message.internal.rabbitmq;

import com.google.common.collect.Lists;
import com.winit.cloudlink.config.Metadata;
import com.winit.cloudlink.config.MqServerOptions;
import com.winit.cloudlink.message.exception.MessageSendException;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionListener;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Created by stvli on 2015/11/21.
 */
public class SwitchableConnectionFactory implements ConnectionFactory {

    private List<DisableCachingConnectionFactory> targetConnectionFactories = Lists.newCopyOnWriteArrayList();
    private DisableCachingConnectionFactory defaultTargetConnectionFactory;

    /**
     * Create a new SwitchableConnectionFactory for the given target
     * ConnectionFactory.
     *
     * @param metadata
     */
    public SwitchableConnectionFactory(Metadata metadata) {
        MqServerOptions serverOptions = metadata.getCurrentZone().getMqServerOptions();
        targetConnectionFactories.add(buildConnectionFactory(serverOptions));
    }

    private DisableCachingConnectionFactory buildConnectionFactory(MqServerOptions serverConfig) {
        DisableCachingConnectionFactory rabbitConnectionFactory = new DisableCachingConnectionFactory(serverConfig.getHost(),
                serverConfig.getPort());
        rabbitConnectionFactory.setVirtualHost(serverConfig.getVirtualHost());
        rabbitConnectionFactory.setUsername(serverConfig.getUsername());
        rabbitConnectionFactory.setPassword(serverConfig.getPassword());
        return rabbitConnectionFactory;
    }

    @Override
    public Connection createConnection() throws AmqpException {
        return determineTargetConnectionFactory().createConnection();
    }

    protected ConnectionFactory determineTargetConnectionFactory() {
        Assert.notEmpty(targetConnectionFactories, "'targetConnectionFactories' must not be empty.");
        for (DisableCachingConnectionFactory rabbitConnectionFactory : targetConnectionFactories) {
            if (!rabbitConnectionFactory.isDisable()) {
                defaultTargetConnectionFactory = rabbitConnectionFactory;
                return rabbitConnectionFactory;
            }
        }
        throw new MessageSendException("Without a valid server.");
    }

    @Override
    public String getHost() {
        return this.determineTargetConnectionFactory().getHost();
    }

    @Override
    public int getPort() {
        return this.determineTargetConnectionFactory().getPort();
    }

    @Override
    public String getVirtualHost() {
        return this.determineTargetConnectionFactory().getVirtualHost();
    }

    @Override
    public void addConnectionListener(ConnectionListener listener) {

    }

    @Override
    public boolean removeConnectionListener(ConnectionListener listener) {
        return false;
    }

    @Override
    public void clearConnectionListeners() {

    }

    public synchronized void fireConnectError() {
        defaultTargetConnectionFactory.setDisable(true);
    }

    public class DisableCachingConnectionFactory extends CachingConnectionFactory {

        private boolean disable = false;

        public DisableCachingConnectionFactory() {
        }

        public DisableCachingConnectionFactory(String hostname, int port) {
            super(hostname, port);
        }

        public DisableCachingConnectionFactory(int port) {
            super(port);
        }

        public DisableCachingConnectionFactory(String hostname) {
            super(hostname);
        }

        public DisableCachingConnectionFactory(com.rabbitmq.client.ConnectionFactory rabbitConnectionFactory) {
            super(rabbitConnectionFactory);
        }

        public boolean isDisable() {
            return disable;
        }

        public void setDisable(boolean disable) {
            this.disable = disable;
        }
    }

}