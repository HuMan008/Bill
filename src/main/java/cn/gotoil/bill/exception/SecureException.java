package cn.gotoil.bill.exception;

/**
 * {类描述}
 *
 * @author SuYajiang syj247@qq.com
 * @Date 2018-11-7 11:10
 */

import cn.gotoil.bill.exception.SecureError;


public class SecureException extends RuntimeException {

    private int tickcode;

    @SuppressWarnings("unused")
    public SecureException(int tickcode, String message) {
        super(message);
        this.tickcode = tickcode;
    }

    public SecureException(SecureError err) {
        super(err.getMessage());
        tickcode = err.getCode();
    }

    public int getTickcode() {
        return tickcode;
    }

}
