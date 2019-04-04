/*
 * Copyright (C) 2017.  Iusworks, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Dolphin cn.gotoil.dolphin.exception.DolphinErrorCommon
 *
 * cluries <cluries@me.com>,  February 2017
 *
 * LastModified: 1/4/17 10:14 AM
 *
 */

package cn.gotoil.bill.exception;


@SuppressWarnings("unused")
public enum CommonError implements BillError {

    Forbidden(403, "Forbidden"),
    NotFound(404, "Not Found"),
    EmptyRequestBody(416, "Empty Request Data"),

    ValidateError(9100, "Input Validate Error"),
    Unsupported(9998, "Unsupported request"),
    Unknown(9999, "Unknow Server Error"),

    Need_Login(10001, "您未登录或登录超时"),
    PERMISSION_ERROR(10006, "权限不足");



    private int code;

    private String message;


    CommonError(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
