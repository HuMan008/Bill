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
    private final static ResponseVerifier responseVerifier = new ResponseVerifier();

    /**
     * 执行交易，返回值不做验签处理，直接使用；
     * 可能会返回空，使用值前需要做判断
     *
     * @param request
     * @return
     */
    public static Response transport(Request request) {
        try {
            String xml = XMLConverter.xmlStringFromRequest(request);
            HttpResponse response = Unirest.post(CCCConfig.getUrl()).body(xml).asString();
            if (response != null && response.getStatus() >= 200 && response.getStatus() < 300) {
                Response fr = XMLConverter.xmlObjectFromResponse((String) response.getBody());
                if (fr.getCode() == 0) {
                    return fr;
                }
            }
            logger.error("{}", response.getBody().toString());
        } catch (Exception ex) {
            logger.error("{}", ex);
        }

        return null;
    }

    /**
     * 执行交易，返回值有验签
     * 可能会返回空，使用值前需要做判断
     * requestStr,responseStr 方便做日志处理
     * @param request
     * @param copartnerPassword
     * @param  requestStr 请求内容
     * @param responseStr 响应内容保
     * @return
     */
    public static Response transport(Request request, String copartnerPassword, String requestStr, String responseStr) {
        try {
            String xml = XMLConverter.xmlStringFromRequest(request);
            requestStr = xml;
            HttpResponse response = Unirest.post(CCCConfig.getUrl()).body(xml).asString();
            if (response != null && response.getStatus() >= 200 && response.getStatus() < 300) {
                Response fr = XMLConverter.xmlObjectFromResponse((String) response.getBody());
                responseStr = (String) response.getBody();
                if (fr.getCode() == 0) {
                    //验签
                    if (!signVerify(fr, copartnerPassword)) {
                        logger.error("{} 验签失败", fr.getOriginXml().toString());
                        return null;
                    }

                    return fr;
                }
            }else{
                responseStr = response==null? "response is null":response.toString();
            }

            logger.error("{}", response.getBody().toString());
        } catch (Exception ex) {
            responseStr  =  ex.toString();
            logger.error("{}", ex);
        }

        return null;
    }

    /**
     * 执行交易，返回值有验签；
     * 可能会返回空，使用值前需要做判断
     *
     * @param request
     * @param copartnerPassword
     * @return
     */
    public static Response transport(Request request, String copartnerPassword) {
        try {
            String xml = XMLConverter.xmlStringFromRequest(request);
            HttpResponse response = Unirest.post(CCCConfig.getUrl()).body(xml).asString();
            if (response != null && response.getStatus() >= 200 && response.getStatus() < 300) {
                Response fr = XMLConverter.xmlObjectFromResponse((String) response.getBody());
                if (fr.getCode() == 0) {
                    //验签
                    if (!signVerify(fr, copartnerPassword)) {
                        logger.error("{} 验签失败", fr.getOriginXml().toString());
                        return null;
                    }

                    return fr;
                }
            }
            logger.error("{}", response.getBody().toString());
        } catch (Exception ex) {
            logger.error("{}", ex);
        }

        return null;
    }


    private static boolean signVerify(Response response, String copartnerPassword) {
        return responseVerifier.verifyResponse(response, copartnerPassword);
    }

}
