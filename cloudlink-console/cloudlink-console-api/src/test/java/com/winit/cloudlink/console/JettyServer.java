package com.winit.cloudlink.console;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class JettyServer {

    private Server server;

    public JettyServer(int port){
        server = new Server(port);

        WebAppContext context = new WebAppContext();
        context.setContextPath("/cloudlink");
        context.setDescriptor("src/main/webapp/WEB-INF/web.xml");
        context.setResourceBase("src/main/webapp");
        context.setParentLoaderPriority(true);
        server.setHandler(context);

    }

    public void start() throws Exception {
        server.start();
        // server.join();
    }

    public void stop() throws Exception {
        server.setStopAtShutdown(true);
    }
}
