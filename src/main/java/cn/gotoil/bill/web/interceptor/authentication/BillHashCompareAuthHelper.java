package cn.gotoil.bill.web.interceptor.authentication;

import cn.gotoil.bill.config.property.BillProperties;

import java.util.List;
import java.util.regex.Pattern;

public class BillHashCompareAuthHelper {

    public static  boolean isSkipBillHashURL(String url, BillProperties billProperties) {
        String prefix = billProperties.getKeyOfHashCompareAuthenticationPathPrefix();
        prefix = prefix.replaceAll("\\*","");
        if (prefix != null && !url.startsWith(prefix)) {
            return true;
        }

        List<String> excepts = billProperties.getExceptBodyContentHttpServletRequestWrapperUrls();

        if (excepts == null || excepts.size() > 0) {
            return false;
        }

        for (String exp : excepts) {
            if (url.equals(exp)) {
                return true;
            }

            if (exp.indexOf("*") != -1) {
                exp = exp.replace("*", ".*");
                if (Pattern.compile(exp).matcher(url).matches()) {
                    return true;
                }
            }
        }

        return false;
    }
}
