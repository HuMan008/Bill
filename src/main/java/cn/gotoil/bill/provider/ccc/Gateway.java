/*
 * Copyright (C) 2016.  Iusworks, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Airjet com.iusworks.airjet.provider.ccc.Gateway
 *
 * cluries <cluries@me.com>,  August 2016
 *
 * LastModified: 8/29/16 1:59 PM
 *
 */

package cn.gotoil.bill.provider.ccc;

import cn.gotoil.bill.ApplicationContextProvider;
import cn.gotoil.bill.config.property.BillProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import javax.smartcardio.Card;
import java.util.List;


class Gateway {

    private static Logger logger = LoggerFactory.getLogger(Gateway.class);

    private final static RequestSigner requestSigner = new RequestSigner();


    private static void initCCCConfig() {
        if (CCCConfig.url != null) {
            return;
        }

        ApplicationContext context = ApplicationContextProvider.getApplicationContext();
        BillProperties billProperties = context.getBean(BillProperties.class);
        CCCConfig.loadFromBillProperties(billProperties);
    }


    public static String relationWithMobileAndUserID(String mobileOrGTUid) {
        initCCCConfig();

        RequestEntity entity = new RequestEntity();
        entity.setRequestId("UserphoneOrUnionidCommutativeQuery");
        entity.putParameter("BuExtend1", mobileOrGTUid);
        Request request = requestSigner.signedRequest(entity);
        Response response = Transport.transport(request);
        if (response == null) {
            return null;
        }
        String rel = response.getParameters().get("BusiExtend");
        if (rel != null) {
            String[] ars = rel.split("~");
            if (ars.length >= 2) {
                if (mobileOrGTUid.length() == 12) {
                    return ars[1];
                }
                return ars[0];
            }
        }

        return rel;
    }


    public static List<Card> bindCards(String mobile) {

        initCCCConfig();

        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setRequestId("UserBindCardQuery");
        requestEntity.putParameter("BuExtend1", mobile);
        Request request = requestSigner.signedRequest(requestEntity);
        try {
            Response r = Transport.transport(request);
            logger.info("{}", r);
        } catch (Exception ex) {
            logger.error("{}", ex);
        }

        return null;
    }


}
