/*
 * Copyright (C) 2017.  Iusworks, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Dolphin cn.gotoil.dolphin.exception.DolphinAuthenticationDebuger
 *
 * cluries <cluries@me.com>,  February 2017
 *
 * LastModified: 1/4/17 10:14 AM
 *
 */

package cn.gotoil.bill.exception;


public class AuthenticationDebuger {

    public static final String AuthenticationDebugerKey = "AuthenticationDebugerKey";

    private String signature;

    private String payload;

    private String key;

    private String random;

    private String message;

    public static String getAuthenticationDebugerKey() {
        return AuthenticationDebugerKey;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getRandom() {
        return random;
    }

    public void setRandom(String random) {
        this.random = random;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "AuthenticationDebuger{" +
                "signature='" + signature + '\'' +
                ", payload='" + payload + '\'' +
                ", key='" + key + '\'' +
                ", random='" + random + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
