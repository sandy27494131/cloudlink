package com.winit.cloudlink.boot;

import com.google.common.io.Files;
import com.winit.cloudlink.config.CloudlinkOptions;
import com.winit.cloudlink.config.Metadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.*;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

/**
 * Created by stvli on 2015/11/13.
 */
public class PropertyCloudlinkOptionsSource implements CloudlinkOptionsSource {
    private Logger logger = LoggerFactory.getLogger(PropertyCloudlinkOptionsSource.class);
    public static final String XML_FILE_EXTENSION = ".xml";
    private Properties properties = new Properties();

    public PropertyCloudlinkOptionsSource(String configFile) {
        File file = new File(configFile);
        InputStream in = null;
        try {
            in = new FileInputStream(file);
            if (XML_FILE_EXTENSION.equalsIgnoreCase(Files.getFileExtension(configFile))) {
                properties.loadFromXML(in);
            } else {
                properties.load(in);
            }
        } catch (FileNotFoundException e) {
            logger.error("load properties from file error.", e);
        } catch (InvalidPropertiesFormatException e) {
            logger.error("load properties from file error.", e);
        } catch (IOException e) {
            logger.error("load properties from file error.", e);
        }
    }

    public PropertyCloudlinkOptionsSource(Properties properties) {
        properties.putAll(properties);
    }


    @Override
    public CloudlinkOptions getCloudlinkOptions() {
        CloudlinkOptions options = new CloudlinkOptions();
        options.setProperties(properties);
        return options;
    }
}
