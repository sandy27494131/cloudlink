package com.winit.cloudlink.command.internal;

import org.junit.Test;

public class CommandMessageHandlerTest {

    @Test
    public void test() {

        /*QueryReceptionListExecutor cmd = new QueryReceptionListExecutor();

        QueryReceptionListParam param = new QueryReceptionListParam();

        Map<String, Object> headers = new HashMap<String, Object>();
        headers.put(MessageHeaders.KEY_MESSAGETYPE, cmd.getName());
        headers.put(MessageHeaders.KEY_FROM_APP, new MessageAddress("oms"));
        headers.put(MessageHeaders.KEY_TO_APP, new MessageAddress("tms"));

        Command<QueryReceptionListParam> command = new Command<QueryReceptionListParam>();
        command.setPayload(param);

        Message<Command<QueryReceptionListParam>> message = new Message<Command<QueryReceptionListParam>>(command,
            new MessageHeaders(headers));
        CommandMessageHandler<Command<QueryReceptionListParam>, QueryReceptionListResult> handler = new CommandMessageHandler<Command<QueryReceptionListParam>, QueryReceptionListResult>(cmd,
            new MessageEngine() {

                @Override
                public void start() {

                }

                @Override
                public void shutdown() {

                }

                @Override
                public void unregister(MessageHandler<? extends Message> messageHandler) {

                }

                @Override
                public void send(Message message) {

                }

                @Override
                public void register(MessageHandler<? extends Message> messageHandler) {

                }

                @Override
                public MessageBuilder newMessageBuilder() {
                    // TODO Auto-generated method stub
                    return null;
                }
            });

        Properties properties = new Properties();
        Metadata metadata = new Metadata(properties);

        ApplicationOptions application = new ApplicationOptions();
        application.setAppId("oms-1");
        application.setAppType("oms");
        application.setAppVersion("1.0.0");
        application.setOrganization("CN");
        application.setOwner("development");
        metadata.setApplicationOptions(application);
        handler.setMetadata(metadata);

        handler.process(message);*/
    }

}
