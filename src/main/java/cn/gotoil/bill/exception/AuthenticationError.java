/*
 * Copyright (C) 2017.  Iusworks, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Dolphin cn.gotoil.dolphin.exception.ValidatorEnum
 *
 * cluries <cluries@me.com>,  February 2017
 *
 * LastModified: 2/14/17 9:36 AM
 *
 */

package cn.gotoil.bill.exception;


public enum AuthenticationError implements BillError {

    SignatureError(9000, "Signature verify faild"),
    Entegrity(9001, "Request verify failed"),
    Timeout(9002, "Timeout"),
    FlightError(9003, "Unkown User"),
    RandomTokenDuplicated(9004, "Duplicated token.");

    private int code;

    private String message;

    AuthenticationError(int code, String message) {
        this.message = message;
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
