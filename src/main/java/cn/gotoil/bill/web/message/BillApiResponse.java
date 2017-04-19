/*
 * Copyright (C) 2017.  Iusworks, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Dolphin cn.gotoil.dolphin.web.message.DolphinResponse
 *
 * cluries <cluries@me.com>,  February 2017
 *
 * LastModified: 2/14/17 9:36 AM
 *
 */

package cn.gotoil.bill.web.message;


import cn.gotoil.bill.exception.BillError;
import cn.gotoil.bill.exception.BillException;

public class BillApiResponse {


    private int status;

    private String message;

    private Object data;

    public BillApiResponse(int status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public BillApiResponse(Object data) {
        this.data = data;
        this.status = 0;
    }

    public BillApiResponse(BillException ex) {
        status = ex.getTickcode();
        message = ex.getMessage();
    }

    public BillApiResponse(BillError logicError) {
        this.status = logicError.getCode();
        this.message = logicError.getMessage();
    }

    public BillApiResponse(BillError logicError, Object data) {
        this.status = logicError.getCode();
        this.message = logicError.getMessage();
        this.data = data;
    }


    public BillApiResponse() {
    }


    @Override
    public String toString() {
        return "BillApiResponse{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BillApiResponse)) return false;

        BillApiResponse that = (BillApiResponse) o;

        if (status != that.status) return false;
        if (message != null ? !message.equals(that.message) : that.message != null) return false;
        return data != null ? data.equals(that.data) : that.data == null;

    }

    @Override
    public int hashCode() {
        int result = status;
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
