package com.winit.cloudlink.message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.winit.cloudlink.common.compress.Compress.CompressCodec;
import com.winit.cloudlink.common.compress.CompressFactory;
import com.winit.cloudlink.common.compress.DefaultCompressFactory;
import com.winit.cloudlink.common.exception.CompressionException;
import com.winit.cloudlink.common.extension.ExtensionLoader;
import com.winit.cloudlink.config.ApplicationOptions;
import com.winit.cloudlink.config.Metadata;
import com.winit.cloudlink.message.exception.MessageSizeTooLargeException;
import com.winit.cloudlink.message.messageevent.ExceptionMessageEvent;
import com.winit.cloudlink.message.messageevent.MessageEvent;
import com.winit.cloudlink.message.messageevent.MessageEventNotifier;
import com.winit.cloudlink.message.messageevent.MessageEventType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by stvli on 2015/11/19.
 */
public class FastJsonMessageConverter implements MessageConverter {

    private static final Log log = LogFactory.getLog(FastJsonMessageConverter.class);

    public static final String DEFAULT_CHARSET = "UTF-8";

    public static final String CONTEXT_ACCEPT_ENCODING = "Accept-Encoding";

    private static CompressFactory compressFactory = new DefaultCompressFactory();

    private volatile String defaultCharset = DEFAULT_CHARSET;
    private boolean createMessageIds = false;
    private boolean messageSizeLimited = Constants.DEFAULT_MESSAGE_SIZE_LIMITED;
    private int messageWarnBytes = Constants.DEFAULT_MESSAGE_WARN_BYTES;
    private int messageMaxBytes = Constants.DEFAULT_MESSAGE_MAX_BYTES;

    private boolean compressEnabled = Constants.DEFAULT_COMPRESSION_ENABLED;
    private CompressCodec compressCodec = Constants.DEFAULT_COMPRESSION_CODEC;
    private int noncompressMaxByte = Constants.DEFAULT_NO_COMPRESSION_SIZE;
    private MessageEventNotifier messageEventNotifier;

    public FastJsonMessageConverter(Metadata metadata) {
        ApplicationOptions applicationOptions = metadata.getApplicationOptions();
        messageSizeLimited = applicationOptions.isMessageSizeLimited();
        messageWarnBytes = applicationOptions.getMessageWarnBytes();
        messageMaxBytes = applicationOptions.getMessageMaxBytes();

        compressCodec = applicationOptions.getCompressCodec();
        noncompressMaxByte = applicationOptions.getNonCompressMaxBytes();
        noncompressMaxByte = applicationOptions.getNonCompressMaxBytes();
        initializeJsonObjectMapper();
        try {
            messageEventNotifier = ExtensionLoader.getExtensionLoader(MessageEventNotifier.class).getDefaultExtension();
        } catch (Throwable throwable) {
            log.warn("The messageEventNotifier not found.");
        }
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
        //ParserConfig.getGlobalInstance().addAccept("com.winit.");
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

        try {
            String jsonString = objectToConvert instanceof String ?
                    (String) objectToConvert :
                    JSON.toJSONString(objectToConvert, SerializerFeature.WriteClassName);
            byte[] bytes = jsonString.getBytes(getDefaultCharset());
            if (bytes != null && messageSizeLimited) {
                String messageSummary = jsonString.substring(100);
                if (bytes.length >= messageMaxBytes) {
                    MessageSizeTooLargeException exception = new MessageSizeTooLargeException(String.format("This message is too large to exceed %d bytes and will be refused to send,message content:%s", messageMaxBytes, messageSummary));
                    messageEventNotifier.notify(new ExceptionMessageEvent(MessageEventType.MESSAGE_SIZE_EXCEED, objectToConvert, exception));
                    throw exception;
                }
                if (bytes.length >= messageWarnBytes) {
                    messageEventNotifier.notify(new MessageEvent(MessageEventType.MESSAGE_SIZE_WARNING, "The message size exceeds the warning threshold,message content:" + messageSummary));
                }
            }
            // 是否对消息体进行压缩
            CompressCodec dataCompressCodec = null;
            if (!compressEnabled || CompressCodec.NONE.equals(compressCodec) || bytes.length < noncompressMaxByte) {
                dataCompressCodec = CompressCodec.NONE;
            } else {
                bytes = compressFactory.compress(compressCodec, bytes);
                dataCompressCodec = compressCodec;
            }
            messageProperties.setHeader(CONTEXT_ACCEPT_ENCODING, dataCompressCodec.name());
            messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
            messageProperties.setContentEncoding(getDefaultCharset());
            if (bytes != null) {
                messageProperties.setContentLength(bytes.length);
            }
            return new Message(bytes, messageProperties);
        } catch (IOException e) {
            throw new MessageConversionException("Failed to convert Message content", e);
        } catch (CompressionException e) {
            throw new MessageConversionException("Failed to compress data", e);
        } catch (MessageSizeTooLargeException e) {
            throw new MessageConversionException("Failed to compress data", e);
        }

    }
}
