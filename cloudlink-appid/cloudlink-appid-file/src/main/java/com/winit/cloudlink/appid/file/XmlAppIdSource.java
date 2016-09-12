package com.winit.cloudlink.appid.file;

import com.winit.cloudlink.appid.AppId;
import com.winit.cloudlink.appid.AppIdSource;
import com.winit.cloudlink.common.utils.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 *
 * Created by stvli on 2015/12/28.
 */
public class XmlAppIdSource implements AppIdSource {
    private static final Logger logger = LoggerFactory.getLogger(XmlAppIdSource.class);
    private static XmlAppIdSource me = new XmlAppIdSource();
    private static final String WINIT_DIRECTORY = "META-INF/winit/";
    private static final String APPID_INTERNAL_DIRECTORY = WINIT_DIRECTORY + "internal/";
    private static final String APPID_FILENAME = APPID_INTERNAL_DIRECTORY + "appid-v1.xml";
    private static final String APPID_FILENAME_DTD = APPID_INTERNAL_DIRECTORY + "appid.dtd";

    private final List<String> regions = new ArrayList<String>();
    private final List<String> countries = new ArrayList<String>();
    private final List<String> appTypes = new ArrayList<String>();
    private final Map<String, AppId> appIds = new LinkedHashMap<String, AppId>();

    private XmlAppIdSource() {
        load();
    }

    public static XmlAppIdSource instance() {
        return me;
    }

    @Override
    public void addRegion(String region) {
        throw new UnsupportedOperationException("Method not supported");
    }

    @Override
    public void addCountry(String region) {
        throw new UnsupportedOperationException("Method not supported");
    }

    @Override
    public void addAppType(String region) {
        throw new UnsupportedOperationException("Method not supported");
    }

    @Override
    public void addAppId(AppId appId) {
        throw new UnsupportedOperationException("Method not supported");
    }

    @Override
    public void removeRegion(String region) {
        throw new UnsupportedOperationException("Method not supported");
    }

    @Override
    public void removeCountry(String region) {
        throw new UnsupportedOperationException("Method not supported");
    }

    @Override
    public void removeAppType(String region) {
        throw new UnsupportedOperationException("Method not supported");
    }

    @Override
    public void removeAppId(AppId appId) {
        throw new UnsupportedOperationException("Method not supported");
    }

    @Override
    public List<String> getRegions() {
        return regions;
    }

    @Override
    public List<String> getCountries() {
        return countries;
    }

    @Override
    public List<String> getAppTypes() {
        return appTypes;
    }

    @Override
    public List<AppId> getAppIds() {
        return CollectionUtils.toList(appIds.values());
    }

    @Override
    public AppId getAppIdByCode(String appCode) {
        return appIds.get(appCode);
    }

    @Override
    public boolean checkRegion(String region) {
        return regions.contains(region);
    }

    @Override
    public boolean checkCountry(String country) {
        return countries.contains(country) || AppId.EMPTY_COUNTRY.equals(country);
    }

    @Override
    public boolean checkAppType(String appType) {
        return appTypes.contains(appType);
    }

    @Override
    public boolean checkAppCode(String appCode) {
        return appIds.containsKey(appCode);
    }

    @Override
    public boolean checkAppId(AppId appId) {
        return appIds.containsValue(appId);
    }

    private void load() {
        InputStream dtd = null;
        InputStream xml = null;
        try {
            ClassLoader classLoader = getClassLoader();
            dtd = classLoader.getResourceAsStream(APPID_FILENAME_DTD);
            xml = classLoader.getResourceAsStream(APPID_FILENAME);
            Element root = XmlUtils.getDocument(dtd, xml).getDocumentElement();
            loadRegions(root);
            loadCountries(root);
            loadAppTypes(root);
            loadAppIds(root);
        } catch (Throwable e) {
            logger.error("Exception when load appId from file: " + APPID_FILENAME + ").", e);
        } finally {
            if (dtd != null) {
                try {
                    dtd.close();
                } catch (IOException e) {
                }
            }
            if (xml != null) {
                try {
                    xml.close();
                } catch (IOException e) {
                }
            }
        }
    }


    private void loadRegions(Element root) throws IllegalAccessException, InvocationTargetException {
        Element element = (Element) root.getElementsByTagName("regions").item(0);
        NodeList list = element.getElementsByTagName("region");
        for (int i = 0, n = list.getLength(); i < n; i++) {
            Node node = list.item(i);
            regions.add(node.getTextContent());
        }
    }

    private void loadCountries(Element root) {
        Element element = (Element) root.getElementsByTagName("countries").item(0);
        NodeList list = element.getElementsByTagName("country");
        for (int i = 0, n = list.getLength(); i < n; i++) {
            Node node = list.item(i);
            if (node instanceof Element) {
                countries.add(node.getTextContent());
            }
        }
    }

    private void loadAppTypes(Element root) {
        Element element = (Element) root.getElementsByTagName("appTypes").item(0);
        NodeList list = element.getElementsByTagName("appType");
        for (int i = 0, n = list.getLength(); i < n; i++) {
            Node node = list.item(i);
            if (node instanceof Element) {
                appTypes.add(node.getTextContent());
            }
        }
    }

    private void loadAppIds(Element root) {
        Element element = (Element) root.getElementsByTagName("appIds").item(0);
        NodeList list = element.getElementsByTagName("appId");
        for (int i = 0, n = list.getLength(); i < n; i++) {
            Node node = list.item(i);
            if (node instanceof Element) {
                Element e = (Element) node;
                String appCode = XmlUtils.getAttribute(e, "name");
                String appIdString = XmlUtils.getAttribute(e, "value");
                String description = XmlUtils.getAttribute(e, "description");
                AppId appId = AppId.parse(appIdString);
                if (!checkRegion(appId.getRegion()) || !checkCountry(appId.getCountry()) || !checkAppType(appId.getAppType())) {
                    logger.error("Invalid appId '" + appId + "'.");
                }
                appId.setDescription(description);
                appIds.put(appCode, appId);
            }
        }
    }


    private ClassLoader getClassLoader() {
        return XmlAppIdSource.class.getClassLoader();
    }
}
