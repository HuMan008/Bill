/*
 * Copyright (C) 2016.  Iusworks, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Airjet com.iusworks.airjet.provider.ccc.RequestXMLConverter
 *
 * cluries <cluries@me.com>,  August 2016
 *
 * LastModified: 8/29/16 9:47 PM
 *
 */

package cn.gotoil.bill.provider.ccc;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;


class XMLConverter {

    private static Logger logger = LoggerFactory.getLogger(XMLConverter.class);

    private static JAXBContext jaxbContext;

    static {
        try {
            jaxbContext = JAXBContext.newInstance(Request.class);
        } catch (Exception ex) {
            logger.error("{}", ex);
        }
    }

    public static String xmlStringFromRequest(Request request) throws Exception {
        Marshaller m = jaxbContext.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        Writer writer = new StringWriter();
        m.marshal(request, writer);
        return writer.toString();
    }

    public static Response xmlObjectFromResponse(String xml) {
        try {
            StringReader stringReader = new StringReader(xml);
            Response response = JAXB.unmarshal(stringReader, Response.class);
            if (response != null) {
                response.setOriginXml(xml);
            }

            return response;
        } catch (Exception ex) {
            logger.error("{}", ex);
            return null;
        }
    }
}

