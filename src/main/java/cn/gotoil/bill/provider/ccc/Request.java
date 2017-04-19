/*
 * Copyright (C) 2016.  Iusworks, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Airjet com.iusworks.airjet.provider.ccc.Request
 *
 * cluries <cluries@me.com>,  August 2016
 *
 * LastModified: 8/29/16 9:37 PM
 *
 */

package cn.gotoil.bill.provider.ccc;


import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
class Request implements Serializable {


    private RequestEntity entity;

    private String time;
    private String md5;
    private String md52;
    private String copartnerId;

    public Request() {
    }


    public Request(RequestEntity entity) {
        this.entity = entity;
    }


    public void setEntity(RequestEntity entity) {
        this.entity = entity;
    }

    @XmlTransient
    public RequestEntity getEntity() {
        return entity;
    }


    @XmlElement(name = "request-id")
    public String getRequestId() {
        return entity.getRequestId();
    }


    @XmlElement(name = "request-flow")
    public String getRequestFlow() {
        return entity.getRequestFlow();
    }


    @XmlElement
    public String getCard() {
        return entity.getCard();
    }


    @XmlElement
    public String getPassword() {
        return entity.getPassword();
    }


    @XmlElement
    public int getMoney() {
        return entity.getMoney();
    }


    @XmlJavaTypeAdapter(ParametersMapAdapter.class)
    public HashMap<String, String> getParameters() {
        return entity.getParameters();
    }


    @XmlElement
    public String getTime() {
        return time;
    }


    @XmlElement
    public String getMd5() {
        return md5;
    }


    @XmlElement(name = "md5-2")
    public String getMd52() {
        return md52;
    }

    @XmlElement(name = "copartner-id")
    public String getCopartnerId() {
        return copartnerId;
    }

    @XmlElement
    public String getMac() {
        return "";
    }

    @XmlElement
    public String getSeq() {
        List<String> seq = entity.getSeq();
        if (seq == null || seq.size() < 1) {
            return "";
        }

        return StringUtils.join(seq, ";");
    }

    @XmlElement
    public String getExtend() {
        return "";
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public void setMd52(String md52) {
        this.md52 = md52;
    }

    public void setCopartnerId(String copartnerId) {
        this.copartnerId = copartnerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Request)) return false;

        Request request = (Request) o;

        if (entity != null ? !entity.equals(request.entity) : request.entity != null) return false;
        if (time != null ? !time.equals(request.time) : request.time != null) return false;
        if (md5 != null ? !md5.equals(request.md5) : request.md5 != null) return false;
        if (md52 != null ? !md52.equals(request.md52) : request.md52 != null) return false;
        return copartnerId != null ? copartnerId.equals(request.copartnerId) : request.copartnerId == null;

    }

    @Override
    public int hashCode() {
        int result = entity != null ? entity.hashCode() : 0;
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (md5 != null ? md5.hashCode() : 0);
        result = 31 * result + (md52 != null ? md52.hashCode() : 0);
        result = 31 * result + (copartnerId != null ? copartnerId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Request{" +
                "entity=" + entity +
                ", time='" + time + '\'' +
                ", md5='" + md5 + '\'' +
                ", md52='" + md52 + '\'' +
                ", copartnerId='" + copartnerId + '\'' +
                '}';
    }
}
