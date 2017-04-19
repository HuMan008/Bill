/*
 * Copyright (C) 2017.  Iusworks, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Dolphin cn.gotoil.dolphin.exception.DolphinException
 *
 * cluries <cluries@me.com>,  February 2017
 *
 * LastModified: 2/14/17 9:36 AM
 *
 */

package cn.gotoil.bill.exception;


public class BillException extends RuntimeException {

    private int tickcode;

    public BillException(int tickcode, String message) {
        super(message);
        this.tickcode = tickcode;
    }

    public BillException(BillError err) {
        super(err.getMessage());
        tickcode = err.getCode();
    }

    public int getTickcode() {
        return tickcode;
    }

}
