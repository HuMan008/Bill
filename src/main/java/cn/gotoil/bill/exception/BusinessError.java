/*
 * Copyright (C) 2017.  Iusworks, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Dolphin cn.gotoil.dolphin.exception.DolphinLogicError
 *
 * cluries <cluries@me.com>,  February 2017
 *
 * LastModified: 1/4/17 10:14 AM
 *
 */

package cn.gotoil.bill.exception;


public interface BusinessError {

    int getCode();

    String getMessage();
}
