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


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.util.HashMap;


@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
class Response implements Serializable {

    @XmlElement(name = "response-id")
    private String responseId;


    @XmlElement(name = "request-flow")
    private String requestFlow;

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
}
