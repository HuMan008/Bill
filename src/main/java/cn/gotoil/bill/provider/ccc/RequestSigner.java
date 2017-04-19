/*
 * Copyright (C) 2016.  Iusworks, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Airjet com.iusworks.airjet.provider.ccc.RequestSigner
 *
 * cluries <cluries@me.com>,  September 2016
 *
 * LastModified: 9/1/16 2:12 PM
 *
 */

package cn.gotoil.bill.provider.ccc;


import cn.gotoil.bill.tools.encoder.Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

class RequestSigner {

    private static Logger logger = LoggerFactory.getLogger(RequestSigner.class);

    private static ThreadLocal<SimpleDateFormat> threadLocalDateFormatter = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        }
    };


    public Request signedRequest(RequestEntity requestEntity) {

        Request request = new Request(requestEntity);
        request.setTime(threadLocalDateFormatter.get().format(new Date()));
        request.setCopartnerId(CCCConfig.getCopartnerId());
        signMD5(request);
        signMD52(request);
        return request;
    }

    private void signMD5(Request request) {
        //[request-id][request-flow][copartner-id][card][copartnerPassword][money][time] [合作方密码]
        String payload = stringfly(request.getRequestId()) +
                stringfly(request.getRequestFlow()) +
                stringfly(request.getCopartnerId()) +
                stringfly(request.getCard()) +
                stringfly(request.getPassword()) +
                String.valueOf(request.getMoney()) +
                request.getTime() +
                CCCConfig.getCopartnerPassword();

//        logger.info("MD5 Payload:{}", payload);
        request.setMd5(Hash.md5(payload));
    }

    private void signMD52(Request request) {
        HashMap<String, String> parameters = request.getParameters();
        String payload;
        if (parameters == null || parameters.size() < 1) {
            payload = CCCConfig.getCopartnerPassword();
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            request.getEntity().getSeq().forEach(key -> stringBuilder.append(parameters.get(key)));
            stringBuilder.append(CCCConfig.getCopartnerPassword());
            payload = stringBuilder.toString();
        }
//        logger.info("MD52 Payload:{}", payload);
        request.setMd52(Hash.md5(payload.toString()));
    }

    private static String stringfly(String value) {
        return null == value ? "" : value;
    }
}
