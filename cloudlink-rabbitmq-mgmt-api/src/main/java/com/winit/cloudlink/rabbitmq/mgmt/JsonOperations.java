package com.winit.cloudlink.rabbitmq.mgmt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;

public class JsonOperations extends BaseFluent {

    private static final Logger logger = LoggerFactory.getLogger(JsonOperations.class);

    public JsonOperations(HttpContext httpContext, RabbitMgmtService mgmtService){
        super(httpContext, mgmtService);
    }

    public String overview(int dataAge, int dataIncr) {
        ClientResponse response = HTTP.GET(String.format("/overview/?lengths_age=%d&lengths_incr=%d&msg_rates_age=%d&msg_rates_incr=%d",
            dataAge,
            dataIncr,
            dataAge,
            dataIncr));
        return convertResponseToString(response);
    }

    public String nodes() {
        ClientResponse response = HTTP.GET("/nodes");
        return convertResponseToString(response);
    }

    public String node(String nodeName, int dataAge, int dataIncr) {
        ClientResponse response = HTTP.GET(String.format("/nodes/%s?node_stats_age=%d&node_stats_incr=%d",
            nodeName,
            dataAge,
            dataIncr));
        return convertResponseToString(response);
    }

    public String shovelStatus() {
        ClientResponse response = HTTP.GET("/shovels");
        return convertResponseToString(response);
    }

    public String shovel() {
        ClientResponse response = HTTP.GET("/shovel");
        return convertResponseToString(response);
    }

    public String queues() {
        ClientResponse response = HTTP.GET("/queues");
        return convertResponseToString(response);
    }

    public String queue(String vhost, String queue, int dataAge, int dataIncr) {
        ClientResponse response = HTTP.GET(String.format("/queues/%s/%s?lengths_age=%d&lengths_incr=%d&msg_rates_age=%d&msg_rates_incr=%d",
            encodeSlashes(vhost),
            queue,
            dataAge,
            dataIncr,
            dataAge,
            dataIncr));
        return convertResponseToString(response);
    }

    public String queueBindings(String vhost, String queue) {
        ClientResponse response = HTTP.GET(String.format("/queues/%s/%s/bindings", encodeSlashes(vhost), queue));
        return convertResponseToString(response);
    }

    public String exchanges() {
        ClientResponse response = HTTP.GET("/exchanges");
        return convertResponseToString(response);
    }

    public String exchange(String vhost, String exchange, int dataAge, int dataIncr) {
        ClientResponse response = HTTP.GET(String.format("/exchanges/%s/%s?msg_rates_age=%d&msg_rates_incr=%d",
            encodeSlashes(vhost),
            exchange,
            dataAge,
            dataIncr));
        return convertResponseToString(response);
    }

    public String exchangeSource(String vhost, String exchange) {
        ClientResponse response = HTTP.GET(String.format("/exchanges/%s/%s/bindings/source",
            encodeSlashes(vhost),
            exchange));
        return convertResponseToString(response);
    }

    public String exchangeDestination(String vhost, String exchange) {
        ClientResponse response = HTTP.GET(String.format("/exchanges/%s/%s/bindings/destination",
            encodeSlashes(vhost),
            exchange));
        return convertResponseToString(response);
    }

    public String channels() {
        ClientResponse response = HTTP.GET("/channels");
        return convertResponseToString(response);
    }

    public String channel(String channel, int dataAge, int dataIncr) {
        ClientResponse response = HTTP.GET(String.format("/channels/%s?msg_rates_age=%d&msg_rates_incr=%d",
            encodeSlashes(channel),
            dataAge,
            dataIncr));
        return convertResponseToString(response);
    }

    public String connections() {
        ClientResponse response = HTTP.GET("/connections");
        return convertResponseToString(response);
    }

    public String connection(String connection, int dataAge, int dataIncr) {
        ClientResponse response = HTTP.GET(String.format("/connections/%s?data_rates_age=%d&data_rates_incr=%d",
            encodeSlashes(connection),
            dataAge,
            dataIncr));
        return convertResponseToString(response);
    }

    private String convertResponseToString(ClientResponse response) {
        if (response.hasEntity() && response.getStatus() == 200) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntityInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            } catch (IOException e) {
                logger.error("", e);
            } finally {
                if (null != reader) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                    }
                }
                if (null != response) {
                    try {
                        response.close();
                    } catch (ClientHandlerException e1) {
                    }
                }
            }

            return sb.toString();
        }
        return null;
    }

}
