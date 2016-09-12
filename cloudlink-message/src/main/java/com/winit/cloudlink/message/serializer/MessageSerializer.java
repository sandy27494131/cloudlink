package com.winit.cloudlink.message.serializer;

/**
 * Created by stvli on 2015/11/7.
 */
public interface MessageSerializer {
    <T> byte[] serialize(T bean);

    <T> T deserialize(byte[] bytes);
}
