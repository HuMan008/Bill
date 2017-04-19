/*
 * Copyright (C) 2016.  Iusworks, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Airjet com.iusworks.airjet.provider.ccc.Transport
 *
 * cluries <cluries@me.com>,  September 2016
 *
 * LastModified: 9/1/16 4:32 PM
 *
 */

package cn.gotoil.bill.provider.ccc;


import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


class Transport {

    private static Logger logger = LoggerFactory.getLogger(Transport.class);

    public static Response transport(Request request) {
        try {
            String xml = XMLConverter.xmlStringFromRequest(request);
            HttpResponse response = Unirest.post(CCCConfig.getUrl()).body(xml).asString();
            if (response.getStatus() >= 200 && response.getStatus() < 300) {
                Response fr = XMLConverter.xmlObjectFromResponse((String) response.getBody());
                if (fr.getCode() == 0) {
                    return fr;
                }
            }
            logger.error("{}", response.getStatusText());
        } catch (Exception ex) {
            logger.error("{}", ex);
        }

        return null;
    }


    private static boolean signVerify() {
        return true;
    }

}
