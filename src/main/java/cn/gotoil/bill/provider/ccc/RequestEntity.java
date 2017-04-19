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
 * LastModified: 8/29/16 2:07 PM
 *
 */

package cn.gotoil.bill.provider.ccc;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


class RequestEntity implements Serializable {

    private String requestId;

    private String requestFlow = "";

    private String card = "";

    private String password = "";

    private int money;

    private HashMap<String, String> parameters = new HashMap<>();

    private List<String> seq = new ArrayList<>();

    public RequestEntity() {

    }

    public RequestEntity(String requestId, String requestFlow, String card, String password, int money, HashMap<String, String> parameters, List<String> seq) {
        this.requestId = requestId;
        this.requestFlow = requestFlow;
        this.card = card;
        this.password = password;
        this.money = money;
        this.parameters = parameters;
        this.seq = seq;
    }

    public RequestEntity putParameter(String key, String value) {
        parameters.put(key, value);
        seq.add(key);
        return this;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getRequestFlow() {
        return requestFlow;
    }

    public void setRequestFlow(String requestFlow) {
        this.requestFlow = requestFlow;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public HashMap<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(HashMap<String, String> parameters) {
        this.parameters = parameters;
    }

    public List<String> getSeq() {
        return seq;
    }

    public void setSeq(List<String> seq) {
        this.seq = seq;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RequestEntity)) return false;

        RequestEntity that = (RequestEntity) o;

        if (money != that.money) return false;
        if (requestId != null ? !requestId.equals(that.requestId) : that.requestId != null) return false;
        if (requestFlow != null ? !requestFlow.equals(that.requestFlow) : that.requestFlow != null) return false;
        if (card != null ? !card.equals(that.card) : that.card != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (parameters != null ? !parameters.equals(that.parameters) : that.parameters != null) return false;
        return seq != null ? seq.equals(that.seq) : that.seq == null;

    }

    @Override
    public int hashCode() {
        int result = requestId != null ? requestId.hashCode() : 0;
        result = 31 * result + (requestFlow != null ? requestFlow.hashCode() : 0);
        result = 31 * result + (card != null ? card.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + money;
        result = 31 * result + (parameters != null ? parameters.hashCode() : 0);
        result = 31 * result + (seq != null ? seq.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RequestEntity{" +
                "requestId='" + requestId + '\'' +
                ", requestFlow='" + requestFlow + '\'' +
                ", card='" + card + '\'' +
                ", password='" + password + '\'' +
                ", money=" + money +
                ", parameters=" + parameters +
                ", seq=" + seq +
                '}';
    }
}
