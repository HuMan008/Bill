package cn.gotoil.bill.config.property;

import cn.gotoil.bill.web.interceptor.authentication.AuthenticationType;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.*;


@Component
@ConfigurationProperties(prefix = "bill")
public class BillProperties {

    private AuthenticationType defaultAuthenticationType;

    private String allowDevSkipSignature;

    private String apiResponseAroundControllerPackagesExecution;

    private String keyOfHashCompareAuthenticationProviderClass;

    private String keyOfHashCompareAuthenticationProviderMethod;

    private List<String> exceptBodyContentHttpServletRequestWrapperUrls = new ArrayList<>();

    private HashMap<String, String> ccc;

    public AuthenticationType getDefaultAuthenticationType() {
        return defaultAuthenticationType;
    }

    public void setDefaultAuthenticationType(AuthenticationType defaultAuthenticationType) {
        this.defaultAuthenticationType = defaultAuthenticationType;
    }

    public String getApiResponseAroundControllerPackagesExecution() {
        return apiResponseAroundControllerPackagesExecution;
    }

    public void setApiResponseAroundControllerPackagesExecution(String apiResponseAroundControllerPackagesExecution) {
        this.apiResponseAroundControllerPackagesExecution = apiResponseAroundControllerPackagesExecution;
    }

    public List<String> getExceptBodyContentHttpServletRequestWrapperUrls() {
        return exceptBodyContentHttpServletRequestWrapperUrls;
    }

    public String getKeyOfHashCompareAuthenticationProviderClass() {
        return keyOfHashCompareAuthenticationProviderClass;
    }

    public void setKeyOfHashCompareAuthenticationProviderClass(String keyOfHashCompareAuthenticationProviderClass) {
        this.keyOfHashCompareAuthenticationProviderClass = keyOfHashCompareAuthenticationProviderClass;
    }

    public String getKeyOfHashCompareAuthenticationProviderMethod() {
        return keyOfHashCompareAuthenticationProviderMethod;
    }

    public void setKeyOfHashCompareAuthenticationProviderMethod(String keyOfHashCompareAuthenticationProviderMethod) {
        this.keyOfHashCompareAuthenticationProviderMethod = keyOfHashCompareAuthenticationProviderMethod;
    }

    public HashMap<String, String> getCcc() {
        return ccc;
    }

    public void setCcc(HashMap<String, String> ccc) {
        this.ccc = ccc;
    }

    public String getAllowDevSkipSignature() {
        return allowDevSkipSignature;
    }

    public void setAllowDevSkipSignature(String allowDevSkipSignature) {
        this.allowDevSkipSignature = allowDevSkipSignature;
    }

    public void setExceptBodyContentHttpServletRequestWrapperUrls(List<String> exceptBodyContentHttpServletRequestWrapperUrls) {
        this.exceptBodyContentHttpServletRequestWrapperUrls = exceptBodyContentHttpServletRequestWrapperUrls;
    }

    @Override
    public String toString() {
        return "BillProperties{" +
                "defaultAuthenticationType=" + defaultAuthenticationType +
                ", allowDevSkipSignature='" + allowDevSkipSignature + '\'' +
                ", apiResponseAroundControllerPackagesExecution='" + apiResponseAroundControllerPackagesExecution + '\'' +
                ", keyOfHashCompareAuthenticationProviderClass='" + keyOfHashCompareAuthenticationProviderClass + '\'' +
                ", keyOfHashCompareAuthenticationProviderMethod='" + keyOfHashCompareAuthenticationProviderMethod + '\'' +
                ", exceptBodyContentHttpServletRequestWrapperUrls=" + exceptBodyContentHttpServletRequestWrapperUrls +
                ", ccc=" + ccc +
                '}';
    }
}
