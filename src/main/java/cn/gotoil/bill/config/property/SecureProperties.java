package cn.gotoil.bill.config.property;

import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 权限控制的 配置
 *
 * @author SuYajiang SYJ247@qq.com
 * @Date 2018-10-30 10:28
 */
@Component
@ConfigurationProperties(prefix = "secure")
public class SecureProperties {

    // 需要拦截的url
    private String filterUrl = "/web/admin";

    //token  失效时间时长
    private int tokenExpirseTimeMinute=30;


    //登录页面请求地址
    private String loginPageUrl = "login";

    // 错误页面URL
    private String errorPageUrl = "error/error";

    //session中的key 不提供set方法
    public static final String SessionKey = "UserInfo";

    private String billWebAuthenicationClass="";
    private String billWebAuthenicationMethod;


    public String getFilterUrl() {
        return filterUrl;
    }

    public void setFilterUrl(String filterUrl) {
        this.filterUrl = filterUrl;
    }

    public String getLoginPageUrl() {
        return loginPageUrl;
    }

    public void setLoginPageUrl(String loginPageUrl) {
        this.loginPageUrl = loginPageUrl;
    }

    public String getSessionKey() {
        return SessionKey;
    }

    public String getErrorPageUrl() {
        return errorPageUrl;
    }

    public void setErrorPageUrl(String errorPageUrl) {
        this.errorPageUrl = errorPageUrl;
    }

    public String getBillWebAuthenicationClass() {
        return billWebAuthenicationClass;
    }

    public void setBillWebAuthenicationClass(String billWebAuthenicationClass) {
        this.billWebAuthenicationClass = billWebAuthenicationClass;
    }

    public String getBillWebAuthenicationMethod() {
        return billWebAuthenicationMethod;
    }

    public void setBillWebAuthenicationMethod(String billWebAuthenicationMethod) {
        this.billWebAuthenicationMethod = billWebAuthenicationMethod;
    }

    public int getTokenExpirseTimeMinute() {
        return tokenExpirseTimeMinute;
    }

    public void setTokenExpirseTimeMinute(int tokenExpirseTimeMinute) {
        this.tokenExpirseTimeMinute = tokenExpirseTimeMinute;
    }
}
