package com.winit.cloudlink.benchmark;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.winit.cloudlink.Cloudlink;
import com.winit.cloudlink.Configuration;
import com.winit.cloudlink.common.utils.PropertiesHelper;

/**
 * Created by stvli on 2015/11/20.
 */
public abstract class BaseBenchmark {

    protected Cloudlink  cloudlink;
    protected Properties properties;

    public BaseBenchmark(){
        this(Constants.DEFAULT_CONFIG_FILE);
    }

    public BaseBenchmark(String configFile){
        this.properties = loadProperties(configFile);
        cloudlink = new Configuration().configure(this.properties).getCloudlink();
    }

    public Cloudlink getCloudlink() {
        return cloudlink;
    }

    public void setCloudlink(Cloudlink cloudlink) {
        this.cloudlink = cloudlink;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public String getAppId() {
        return cloudlink.getMetadata().getApplicationOptions().getAppId2String();
    }

    public String getToAppId() {
        return properties.getProperty(Constants.KEY_TO_APP_ID);
    }

    public int getMessageSize() {
        return PropertiesHelper.getInt(Constants.KEY_MESSAGE_SIZE, properties, Constants.DEFAULT_MESSAGE_SIZE);
    }

    public boolean isMessageSaved() {
        return PropertiesHelper.getBoolean(Constants.KEY_MESSAGE_SAVED, properties, Constants.DEFAULT_MESSAGE_SAVED);
    }

    public int getSendSecondes() {
        return PropertiesHelper.getInt(Constants.KEY_SEND_SECONDES, properties, Constants.DEFAULT_SEND_SECONDES);
    }


    public String getMessageType() {
        return properties.getProperty(Constants.KEY_MESSAGE_TYPE, Constants.DEFAULT_MESSAGE_TYPE);
    }

    protected Properties loadProperties(String filePath) {
        Properties properties = new Properties();
        InputStream in = null;
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                throw new FileNotFoundException("The config file not exists in current dir.");
            }
            in = new FileInputStream(file);
            properties.load(in);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return properties;
    }
}
