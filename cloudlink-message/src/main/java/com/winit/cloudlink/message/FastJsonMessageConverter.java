package com.winit.cloudlink.message;

import java.io.IOException;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.winit.cloudlink.common.compress.Compress.CompressCodec;
import com.winit.cloudlink.common.compress.CompressFactory;
import com.winit.cloudlink.common.compress.DefaultCompressFactory;
import com.winit.cloudlink.common.exception.CompressionException;
import com.winit.cloudlink.config.Metadata;

/**
 * Created by stvli on 2015/11/19.
 */
public class FastJsonMessageConverter implements MessageConverter {

    private static final Log       log                     = LogFactory.getLog(Jackson2JsonMessageConverter.class);

    public static final String     DEFAULT_CHARSET         = "UTF-8";

    public static final String     CONTEXT_ACCEPT_ENCODING = "Accept-Encoding";

    private static CompressFactory compressFactory         = new DefaultCompressFactory();

    private volatile String        defaultCharset          = DEFAULT_CHARSET;
    private boolean                createMessageIds        = false;

    private CompressCodec          compressCodec           = CompressCodec.NONE;

    private int                    noncompressMaxByte      = 0;

    public FastJsonMessageConverter(Metadata metadata){
        compressCodec = metadata.getApplicationOptions().getCompressCodec();
        noncompressMaxByte = metadata.getApplicationOptions().getNonCompressMaxByte();
        initializeJsonObjectMapper();
    }

    /**
     * Specify the default charset to use when converting to or from text-based
     * Message body content. If not specified, the charset will be "UTF-8".
     *
     * @param defaultCharset The default charset.
     */
    public void setDefaultCharset(String defaultCharset) {
        this.defaultCharset = (defaultCharset != null) ? defaultCharset : DEFAULT_CHARSET;
    }

    public String getDefaultCharset() {
        return defaultCharset;
    }

    /**
     * Flag to indicate that new messages should have unique identifiers added
     * to their properties before sending. Default false.
     *
     * @param createMessageIds the flag value to set
     */
    public void setCreateMessageIds(boolean createMessageIds) {
        this.createMessageIds = createMessageIds;
    }

    /**
     * Flag to indicate that new messages should have unique identifiers added
     * to their properties before sending.
     *
     * @return the flag value
     */
    protected boolean isCreateMessageIds() {
        return createMessageIds;
    }

    /**
     * Subclass and override to customize.
     */
    protected void initializeJsonObjectMapper() {
    }

    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        Object content = null;
        MessageProperties properties = message.getMessageProperties();
        if (properties != null) {
            String contentType = properties.getContentType();
            if (contentType != null && contentType.contains("json")) {
                String encoding = properties.getContentEncoding();
                if (encoding == null) {
                    encoding = getDefaultCharset();
                }
                try {
                    byte[] body = decompressBytes(message.getBody(), properties);

                    content = convertBytesToObject(body, encoding);
                } catch (IOException e) {
                    throw new MessageConversionException("Failed to convert Message content", e);
                } catch (CompressionException e) {
                    throw new MessageConversionException("Failed to extract data", e);
                }
            } else {
                log.warn("Could not convert incoming message with content-type [" + contentType + "]");
            }
        }
        if (content == null) {
            content = message.getBody();
        }
        return content;
    }

    public final Message toMessage(Object object,
                                   MessageProperties messageProperties) throws MessageConversionException {
        if (messageProperties == null) {
            messageProperties = new MessageProperties();
        }
        Message message = createMessage(object, messageProperties);
        messageProperties = message.getMessageProperties();
        if (this.createMessageIds && messageProperties.getMessageId() == null) {
            messageProperties.setMessageId(UUID.randomUUID().toString());
        }
        return message;
    }

    private byte[] decompressBytes(byte[] body, MessageProperties messageProperties) throws CompressionException {
        Object acceptEncoding = messageProperties.getHeaders().get(CONTEXT_ACCEPT_ENCODING);
        if (null == acceptEncoding) {
            return body;
        }

        String compressCodec = acceptEncoding.toString();

        CompressCodec type = CompressCodec.nameOf(compressCodec);

        return compressFactory.decompress(type, body);
    }

    private Object convertBytesToObject(byte[] body, String encoding) throws IOException {
        String contentAsString = new String(body, encoding);
        log.info("Received message : " + contentAsString);
        return JSON.parse(contentAsString);
    }

    protected Message createMessage(Object objectToConvert,
                                    MessageProperties messageProperties) throws MessageConversionException {
        byte[] bytes = null;
        CompressCodec dataCompressCodec = null;
        try {
            String jsonString = JSON.toJSONString(objectToConvert, SerializerFeature.WriteClassName);

            bytes = jsonString.getBytes(getDefaultCharset());

            // 大于伐值才进行压缩数据o
            if (!CompressCodec.NONE.equals(compressCodec) && bytes.length > noncompressMaxByte) {
                bytes = compressFactory.compress(compressCodec, bytes);
                dataCompressCodec = compressCodec;
            } else {
                dataCompressCodec = CompressCodec.NONE;
            }

        } catch (IOException e) {
            throw new MessageConversionException("Failed to convert Message content", e);
        } catch (CompressionException e) {
            throw new MessageConversionException("Failed to compress data", e);
        }
        messageProperties.setHeader(CONTEXT_ACCEPT_ENCODING, dataCompressCodec.name());
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
        messageProperties.setContentEncoding(getDefaultCharset());
        if (bytes != null) {
            messageProperties.setContentLength(bytes.length);
        }
        return new Message(bytes, messageProperties);
    }
}
