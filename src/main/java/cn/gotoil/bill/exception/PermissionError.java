package cn.gotoil.bill.exception;

/**
 * {类描述}
 *
 * @author SuYajiang SYJ247@qq.com
 * @Date 2018-10-30 11:23
 */

public enum PermissionError implements SecureError {
    Need_Login(55, "您未登录或登录超时"),
    PERMISSION_ERROR(403, "权限不足");
    private int code;

    private String message;

    PermissionError() {
    }


    PermissionError(int code, String msg) {
        this.code = code;
        this.message = msg;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
