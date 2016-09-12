package com.winit.cloudlink.console;


public class Startup {

    public static void main(String[] args) {
        JettyServer server = new JettyServer(8089);
        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
