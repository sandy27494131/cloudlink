package com.winit.cloudlink.message.serializer;

import com.alibaba.fastjson.JSON;

/**
 * Created by stvli on 2015/11/7.
 */
public class JsonSerializer implements MessageSerializer {
    @Override
    public <T> byte[] serialize(T bean) {
        return JSON.toJSONBytes(bean);
    }

    @Override
    public <T> T deserialize(byte[] bytes) {
        return (T) JSON.parse(bytes);
    }
}
