package com.winit.cloudlink.rabbitmq.mgmt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.GenericType;
import com.winit.cloudlink.rabbitmq.httpclient.BasicAuthHttpClientProvider;
import com.winit.cloudlink.rabbitmq.httpclient.HttpClientProvider;
import com.winit.cloudlink.rabbitmq.httpclient.SslWithBasicAuthHttpClientProvider;
import com.winit.cloudlink.rabbitmq.mgmt.model.Overview;

/**
 * Java binding to the RabbitMQ Management Service.
 * 
 * @author Richard Clayton (Berico Technologies)
 */
public class RabbitMgmtService {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMgmtService.class);

    HttpClientProvider          httpClientProvider;
    String                      hostname;
    int                         port;
    private String              vhost;
    HttpContext                 httpContext;

    public RabbitMgmtService(){
    }

    public RabbitMgmtService(String hostname, int port, String vhost, HttpClientProvider httpClientProvider){

        this.hostname = hostname;
        this.port = port;
        this.vhost = vhost;
        this.httpClientProvider = httpClientProvider;
    }

    /**
     * This needs to be called when you are done setting config properties.
     */
    public RabbitMgmtService initialize() {

        Client client = httpClientProvider.getClient();

        String protocol = (httpClientProvider.useSsl()) ? "https" : "http";

        httpContext = new HttpContext(client, protocol, hostname, port);

        logger.info("RabbitMgmtService initialized.");

        return this;
    }

    /**
     * Set the client provider.
     * 
     * @param httpClientProvider Interface that allows you to generically
     * configure the underlying Jersey Client provider.
     */
    public void setHttpClientProvider(HttpClientProvider httpClientProvider) {
        this.httpClientProvider = httpClientProvider;
    }

    /**
     * Set the name of the RabbitMQ broker with the MGMT console to connect to.
     * 
     * @param hostname DNS name of the host.
     */
    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    /**
     * Set the port of the RabbitMQ Management Console.
     * 
     * @param port Port of the RabbitMQ Management Console.
     */
    public void setPort(int port) {
        this.port = port;
    }

    public String getHostname() {
        return hostname;
    }

    public int getPort() {
        return port;
    }

    public String getVhost() {
        return vhost;
    }

    public void setVhost(String vhost) {
        this.vhost = vhost;
    }

    /**
     * Get the HttpContext we're using to make calls and deserialize payloads.
     * 
     * @return HttpContext
     */
    public HttpContext getHttpContext() {

        return this.httpContext;
    }

    /**
     * Get the overview for this node.
     * 
     * @return Overview of the node with stats about usage.
     */
    public Overview overview() {

        checkInitialize();

        return this.httpContext.GET("/overview", new GenericType<Overview>() {
        }).get();
    }

    /**
     * 获取最后一定时间段内的overview
     * @return
     */
    public Overview overviewBySeconds(long seconds){
        checkInitialize();

        return this.httpContext.GET(String.format("/overview?lengths_age=%s&lengths_incr=5&msg_rates_age=%s&msg_rates_incr=5"
                                        ,new Object[]{String.valueOf(seconds),String.valueOf(seconds)})
                , new GenericType<Overview>() {
        }).get();
    }
    /**
     * Get operations related to exchanges.
     * 
     * @return Exchange Operations
     */
    public ExchangeOperations exchanges() {

        checkInitialize();

        return new ExchangeOperations(httpContext, this);
    }

    /**
     * Get operations related to queues.
     * 
     * @return Queue Operations
     */
    public QueueOperations queues() {

        checkInitialize();

        return new QueueOperations(httpContext, this);
    }

    /**
     * Get operations related to vhosts.
     * 
     * @return VHost Operations
     */
    public VirtualHostOperations vhosts() {

        checkInitialize();

        return new VirtualHostOperations(httpContext, this);
    }

    /**
     * Get operations related to users.
     * 
     * @return User Operations
     */
    public UserOperations users() {

        checkInitialize();

        return new UserOperations(httpContext, this);
    }

    /**
     * Get operations related to permissions.
     * 
     * @return Permission Operations
     */
    public PermissionOperations permissions() {

        checkInitialize();

        return new PermissionOperations(httpContext, this);
    }

    /**
     * Get operations related to nodes.
     * 
     * @return Node Operations
     */
    public NodeOperations nodes() {

        checkInitialize();

        return new NodeOperations(httpContext, this);
    }

    /**
     * Get operations related to bindings.
     * 
     * @return Binding Operations
     */
    public BindingOperations bindings() {

        checkInitialize();

        return new BindingOperations(httpContext, this);
    }

    /**
     * Get operations related to parameters.
     * 
     * @return Parameter Operations.
     */
    public ParameterOperations parameters() {

        checkInitialize();

        return new ParameterOperations(httpContext, this);
    }

    /**
     * Get operations related to parameters.
     * 
     * @return Connection Operations.
     */
    public ConnectionOperations connections() {

        checkInitialize();

        return new ConnectionOperations(httpContext, this);
    }
    public ChannelOperations channels()
    {
        checkInitialize();;
        return new ChannelOperations(httpContext,this);
    }
    /**
     * Get operations related to federating AMQP exchanges and queues between
     * brokers/clusters.
     * 
     * @return Federation Operations
     */
    public FederationOperations federation() {

        checkInitialize();

        return new FederationOperations(httpContext, this);
    }

    /**
     * Get operations related to shovel AMQP exchanges and queues between
     * brokers/clusters.
     * 
     * @return Shovel Operations
     */
    public ShovelOperations shovel() {

        checkInitialize();

        return new ShovelOperations(httpContext, this);
    }

    /**
     * Declares a test queue, then publishes and consumes a message. Intended
     * for use by monitoring tools. If everything is working correctly, will
     * return HTTP status 200
     * 
     * @return Shovel Operations
     */
    public AlivenessOperations aliveness() {

        checkInitialize();

        return new AlivenessOperations(httpContext, this);
    }
    
    public JsonOperations json() {

        checkInitialize();

        return new JsonOperations(httpContext, this);
    }

    private void checkInitialize() {

        if (httpContext == null) initialize();
    }

    /**
     * Get a builder for the RabbitMgmtService, which can be a little tedious to
     * configure via constructor.
     * 
     * @return AmqpUri instance.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Makes RabbitMgmtService construction easier.
     */
    public static class Builder {

        private String             host     = "localhost";

        private int                port     = 15672;

        private String             vhost    = "/";

        private HttpClientProvider provider = new BasicAuthHttpClientProvider("guest", "guest");

        public Builder host(String host) {

            this.host = host;

            return this;
        }

        public Builder port(int port) {

            this.port = port;

            return this;
        }

        public Builder vhost(String vhost) {

            this.vhost = vhost;

            return this;
        }

        public Builder credentials(String username, String password) {

            this.provider = new BasicAuthHttpClientProvider(username, password);

            return this;
        }

        public Builder credentials(String key, String keyPass, String trust, String username, String password) {

            return credentials(key, keyPass, trust, null, username, password);
        }

        public Builder credentials(String key, String keyPass, String trust, String trustPass, String username,
                                   String password) {

            this.provider = new SslWithBasicAuthHttpClientProvider(key, keyPass, trust, trustPass, username, password);

            return this;
        }

        public RabbitMgmtService build() {

            return new RabbitMgmtService(host, port, vhost, provider);
        }
    }

    public void destroy() {
        Client client = getClient();
        if (null != client) {
            client.destroy();
        }
    }

    public Client getClient() {
        if (null != this.httpContext) {
            return this.httpContext.getClient();
        }
        return null;
    }
}
