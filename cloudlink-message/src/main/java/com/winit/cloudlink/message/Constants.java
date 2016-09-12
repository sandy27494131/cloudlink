package com.winit.cloudlink.message;

/**
 * Constants
 *
 * @author Steven.Liu
 */
public class Constants extends com.winit.cloudlink.common.Constants {

    public static final String MESSAGE_DEFAULT_SEND_EXCHANGE_NAME         = "winit_send";
    public static final String MESSAGE_DEFAULT_RECV_EXCHANGE_NAME         = "winit_receive";
    public static final String EVENT_DEFAULT_SEND_EXCHANGE_NAME           = "event_send";
    public static final String EVENT_DEFAULT_RECV_EXCHANGE_NAME           = "event_receive";
    public static final String MESSAGE_COMPRESS_STARTBYTE                 = "message.compress.startbyte";
    public static final String MESSAGE_ROUTING_KEY_SEPARATOR              = ".";
    public static final int    MESSAGE_SEND_RETRY_COUNT                   = 3;
    public static final String MESSAGE_CONNECTION_SENDPOOL_MAXTOTALPERKEY = "message.connection.sendpool.maxtotalperkey";

    public static final String MESSAGE_CHANNEL_SENDPOOL_MAXTOTALPERKEY    = "message.channel.sendpool.maxtotalperkey";

    public static final String MESSAGE_CONNECTION_SENDPOOL_MAXIDLEPERKEY  = "message.connection.sendpool.maxIdlePerKey";

    public static final String MESSAGE_CHANNEL_SENDPOOL_MAXIDLEPERKEY     = "message.channel.sendpool.maxIdlePerKey";
    public static final String MESSAGE_FANOUT_APPID                       = "ALL";
    public static final String MESSAGE_DEFAULT_BINDINGKEY_SUFFIX          = "#.";

    public static final String MESSAGE_SHOVEL_QUEUE_PREFIX                = "WINIT.SHOVEL.TO.";

    public static final String EVENT_SHOVEL_QUEUE_PREFIX                  = "EVENT.SHOVEL.TO.";

    public static final String EVENT_SEND_TO_RECEIVE_BINDINGKEY           = "*.EVT.#.%s.#";

    public static final String EVENT_QUEUE_KEYWORD                        = "EVT";

    public static final String EVENT_QUEUE_ROUTINGKEY_SUFFIX              = "#";

    public static final String EVNET_RONTINGKEY_UNKNOW                    = "UNKNOW";

    public static final String EVENT_STATUS_SUFFIX                        = "STATUS";
}
