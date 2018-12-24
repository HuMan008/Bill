/*
 * Copyright (C) 2016.  Iusworks, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Airjet com.iusworks.airjet.provider.ccc.Response
 *
 * cluries <cluries@me.com>,  August 2016
 *
 * LastModified: 8/29/16 2:07 PM
 *
 */

package cn.gotoil.bill.provider.ccc;


import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;


@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
class Response implements Serializable {

    @XmlElement(name = "response-id")
    private String responseId;


    @XmlElement(name = "request-flow")
    private String requestFlow;

    /**
     * @sine 1.0.3
     * @add Suyj
     * 响应流水号
     */
    @XmlElement(name ="response-flow" )
    private String responseFlow;


    @XmlElement(name = "copartner-id")
    private String copartnerId;

    @XmlElement
    private String card;

    @XmlElement
    private Integer money;

    @XmlElement
    private Integer balance;

    @XmlElement
    private Integer taxbal;

    @XmlElement
    private Integer code;

    @XmlElement
    private String description;

    @XmlJavaTypeAdapter(ParametersMapAdapter.class)
    private HashMap<String, String> parameters;

    @XmlElement
    private String mac;

    @XmlElement
    private String md5;

    @XmlElement
    private String time;

    @XmlElement
    private String extend;

    @XmlElement
    private String extend2;

    @XmlElement
    private String seq;

    @XmlElement(name = "md5-2")
    private String md52;

    private String originXml;

    public void setOriginXml(String originXml) {
        this.originXml = originXml;
    }

    public String getResponseId() {
        return responseId;
    }

    public String getRequestFlow() {
        return requestFlow;
    }

    public String getResponseFlow() {
        return responseFlow;
    }

    public String getCopartnerId() {
        return copartnerId;
    }

    public String getCard() {
        return card;
    }

    public Integer getMoney() {
        return money;
    }

    public Integer getBalance() {
        return balance;
    }

    public Integer getTaxbal() {
        return taxbal;
    }

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public HashMap<String, String> getParameters() {
        return parameters;
    }

    public String getMac() {
        return mac;
    }

    public String getMd5() {
        return md5;
    }

    public String getTime() {
        return time;
    }

    public String getExtend() {
        return extend;
    }

    public String getExtend2() {
        return extend2;
    }

    public String getSeq() {
        return seq;
    }

    public String getMd52() {
        return md52;
    }

    public String getOriginXml() {
        return originXml;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Response)) return false;

        Response response = (Response) o;

        if (responseId != null ? !responseId.equals(response.responseId) : response.responseId != null) return false;
        if (requestFlow != null ? !requestFlow.equals(response.requestFlow) : response.requestFlow != null)
            return false;
        if (responseFlow != null ? !responseFlow.equals(response.responseFlow) : response.responseFlow != null)
            return false;
        if (copartnerId != null ? !copartnerId.equals(response.copartnerId) : response.copartnerId != null)
            return false;
        if (card != null ? !card.equals(response.card) : response.card != null) return false;
        if (money != null ? !money.equals(response.money) : response.money != null) return false;
        if (balance != null ? !balance.equals(response.balance) : response.balance != null) return false;
        if (taxbal != null ? !taxbal.equals(response.taxbal) : response.taxbal != null) return false;
        if (code != null ? !code.equals(response.code) : response.code != null) return false;
        if (description != null ? !description.equals(response.description) : response.description != null)
            return false;
        if (parameters != null ? !parameters.equals(response.parameters) : response.parameters != null) return false;
        if (mac != null ? !mac.equals(response.mac) : response.mac != null) return false;
        if (md5 != null ? !md5.equals(response.md5) : response.md5 != null) return false;
        if (time != null ? !time.equals(response.time) : response.time != null) return false;
        if (extend != null ? !extend.equals(response.extend) : response.extend != null) return false;
        if (extend2 != null ? !extend2.equals(response.extend2) : response.extend2 != null) return false;
        if (seq != null ? !seq.equals(response.seq) : response.seq != null) return false;
        if (md52 != null ? !md52.equals(response.md52) : response.md52 != null) return false;
        return originXml != null ? originXml.equals(response.originXml) : response.originXml == null;

    }

    @Override
    public int hashCode() {
        int result = responseId != null ? responseId.hashCode() : 0;
        result = 31 * result + (requestFlow != null ? requestFlow.hashCode() : 0);
        result = 31 * result + (responseFlow != null ? responseFlow.hashCode() : 0);
        result = 31 * result + (copartnerId != null ? copartnerId.hashCode() : 0);
        result = 31 * result + (card != null ? card.hashCode() : 0);
        result = 31 * result + (money != null ? money.hashCode() : 0);
        result = 31 * result + (balance != null ? balance.hashCode() : 0);
        result = 31 * result + (taxbal != null ? taxbal.hashCode() : 0);
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (parameters != null ? parameters.hashCode() : 0);
        result = 31 * result + (mac != null ? mac.hashCode() : 0);
        result = 31 * result + (md5 != null ? md5.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (extend != null ? extend.hashCode() : 0);
        result = 31 * result + (extend2 != null ? extend2.hashCode() : 0);
        result = 31 * result + (seq != null ? seq.hashCode() : 0);
        result = 31 * result + (md52 != null ? md52.hashCode() : 0);
        result = 31 * result + (originXml != null ? originXml.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Response{" +
                "responseId='" + responseId + '\'' +
                ", requestFlow='" + requestFlow + '\'' +
                ", responseFlow='" + responseFlow + '\'' +
                ", copartnerId='" + copartnerId + '\'' +
                ", card='" + card + '\'' +
                ", money=" + money +
                ", balance=" + balance +
                ", taxbal=" + taxbal +
                ", code=" + code +
                ", description='" + description + '\'' +
                ", parameters=" + parameters +
                ", mac='" + mac + '\'' +
                ", md5='" + md5 + '\'' +
                ", time='" + time + '\'' +
                ", extend='" + extend + '\'' +
                ", extend2='" + extend2 + '\'' +
                ", seq='" + seq + '\'' +
                ", md52='" + md52 + '\'' +
                ", originXml='" + originXml + '\'' +
                '}';
    }

//    public static void main(String[] args) {
//        String x ="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
//                "<response>\n" +
//                "<response-id>MerchantOilstorageCodeRuleQuery</response-id>\n" +
//                "<request-flow></request-flow>\n" +
//                "<response-flow></response-flow>\n" +
//                "<copartner-id>Z0000114</copartner-id>\n" +
//                "<card></card>\n" +
//                "<money>0</money>\n" +
//                "<balance>0</balance>\n" +
//                "<taxbal>0</taxbal>\n" +
//                "<parameters>\n" +
//                "<parameter name=\"BusiExtend\" value=\"141~1000~12~364~1000~364~930~0~0~1~1000~2018-03-13 15:52:13~0|142~3000~12~364~3000~364~2790~1~1~0~3000~2018-03-13 15:52:07~0|143~5000~12~364~5000~364~4650~1~1~1~5000~2018-03-13 15:52:00~0|144~8000~12~364~8000~364~7440~1~1~1~8000~2018-03-13 15:51:54~0|145~10000~12~364~10000~364~9300~1~1~1~10000~2018-03-13 15:51:49~1|146~20000~12~364~20000~364~18600~1~1~0~20000~2018-03-13 15:51:42~1|122~90000~12~364~90000~364~11~0~0~1~1123~2018-03-01 15:01:16~0|116~1000~12~364~920~364~910~0~0~1~w~2018-03-01 14:49:52~0|117~2000~12~360~1840~364~1800~0~0~1~1~2018-03-01 14:49:48~0|118~3000~12~364~2760~364~2700~0~0~1~1~2018-03-01 14:49:44~0|119~5000~12~364~4600~364~4500~0~0~1~2~2018-03-01 14:49:40~0|120~8000~12~364~7360~364~7300~0~0~0~ss~2018-03-01 14:49:36~0|121~10000~12~364~9200~364~9000~0~0~0~12~2018-03-01 14:49:32~0|115~56800售价50000~12~364~56800~364~50000~0~0~0~56800售价50000~2018-02-28 14:41:00~0|112~5338售价5000~12~364~5338~364~5000~0~0~0~5338售价5000~2018-02-28 14:39:45~0|113~12000售价11000~12~364~12000~364~10000~0~0~0~12000售价11000~2018-02-28 14:39:41~0|114~28000售价25000~12~364~28000~364~25000~0~0~1~28000售价25000~2018-02-28 14:39:37~0|111~2118售价2000~12~364~2118~364~2000~0~0~0~2118售价2000~2018-02-28 14:39:32~0|110~3500售价3000~12~364~3500~364~3000~0~0~0~3500售价3000~2018-02-28 14:25:59~0|109~2100售价2000~12~364~2100~364~2000~0~0~0~2100售价2000~2018-02-28 14:22:39~0|108~1050售价1000~12~364~1050~364~1000~0~0~0~测试~2018-02-28 14:07:04~0|102~一千~12~364~1000~364~900~0~0~0~一千~2018-02-11 10:10:33~0|103~两千~12~364~1000~364~900~0~0~0~两千~2018-02-11 10:10:29~0|104~三千~12~364~3000~364~2900~0~0~0~三千~2018-02-11 10:10:25~0|105~四千~12~364~4000~364~3600~0~0~0~四千~2018-02-11 10:10:21~0|106~五千~12~364~5000~364~4000~0~0~0~五千~2018-02-11 10:10:17~0|107~一万~12~364~10000~364~9000~0~0~1~一万~2018-02-11 10:10:13~0|101~啊啊~12~364~300~364~200~0~0~0~斯蒂芬森~2018-02-09 18:57:37~1|94~1000元~11~0~0~364~1000~0~0~0~1000~2018-02-09 16:43:07~0|95~2000~11~0~0~364~2000~0~0~0~2000~2018-02-09 16:43:02~0|96~3000元~11~0~0~364~3000~0~0~0~3000~2018-02-09 16:42:56~0|97~5000元~11~0~0~364~5000~0~0~0~5000~2018-02-09 16:42:51~0|98~8000元~11~0~0~364~8000~0~0~0~8000~2018-02-09 16:42:46~0|99~10000元~11~0~0~364~10000~0~0~0~10000~2018-02-09 16:42:42~0|72~123~12~100~300~364~200~1~1~0~小可爱的规则~2018-01-30 10:47:37~0\"></parameter>\n" +
//                "</parameters>\n" +
//                "<code>0</code>\n" +
//                "<description>查询成功</description>\n" +
//                "<mac></mac>\n" +
//                "<md5>d81ad959f1eaaf3fc515d06a7ef19a3c</md5>\n" +
//                "<time>2018.12.24 09:41:15</time>\n" +
//                "<extend><![CDATA[]]></extend>\n" +
//                "<extend2><![CDATA[]]></extend2>\n" +
//                "<seq>BusiExtend</seq>\n" +
//                "<md5-2>7f4756789ea0fa8b93c000557d12b341</md5-2>\n" +
//                "</response>\n";
//
//        Response response=XMLConverter.xmlObjectFromResponse(x);
//        System.out.printf(response.toString());
//    }
}
