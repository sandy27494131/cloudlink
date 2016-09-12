package com.winit.cloudlink.appid.file;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by stvli on 2015/12/30.
 */
public class XmlUtils {
    public static Document getDocument(final InputStream dtd, InputStream xml) throws ParserConfigurationException,
            SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(true);
        factory.setNamespaceAware(false);
        DocumentBuilder builder = factory.newDocumentBuilder();
        builder.setEntityResolver(new EntityResolver() {
            @Override
            public InputSource resolveEntity(String publicId, String systemId) {
                return new InputSource(dtd);
            }
        });
        builder.setErrorHandler(new ErrorHandler() {
            @Override
            public void warning(SAXParseException e) {
            }

            @Override
            public void error(SAXParseException e) throws SAXException {
                throw e;
            }

            @Override
            public void fatalError(SAXParseException e) throws SAXException {
                throw e;
            }
        });
        return builder.parse(xml);
    }


    public static String getAttribute(Element e, String name) {
        return e.getAttribute(name);
    }

}
