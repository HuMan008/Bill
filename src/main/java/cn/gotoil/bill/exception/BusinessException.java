package cn.gotoil.bill.exception;

/**
 * 业务中的异常
 *
 * @author SuYajiang syj247@qq.com
 * @Date 2018-11-7 11:10
 */


public class BusinessException extends RuntimeException {

    private int tickcode;

    @SuppressWarnings("unused")
    public BusinessException(int tickcode, String message) {
        super(message);
        this.tickcode = tickcode;
    }

    public BusinessException(SecureError err) {
        super(err.getMessage());
        tickcode = err.getCode();
    }

    public int getTickcode() {
        return tickcode;
    }

}
