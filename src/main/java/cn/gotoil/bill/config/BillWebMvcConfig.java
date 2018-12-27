package cn.gotoil.bill.config;

import cn.gotoil.bill.config.property.BillProperties;
import cn.gotoil.bill.config.property.SecureProperties;
import cn.gotoil.bill.web.filter.HttpBodyStreamWrapperFilter;
import cn.gotoil.bill.web.interceptor.authentication.hashcompare.HashcompareAuthenticationInterceptor;
import cn.gotoil.bill.web.interceptor.secure.PermissionInterceptor;
import cn.gotoil.bill.web.interceptor.secure.RoleInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.session.web.http.SessionRepositoryFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.*;

import java.text.SimpleDateFormat;
import java.util.List;

//@Configuration
//@EnableWebMvc
@Component
public class BillWebMvcConfig extends WebMvcConfigurationSupport {

    @Autowired
    private BillProperties billProperties;

    @Autowired
    private HttpBodyStreamWrapperFilter wrapperFilter;

    @Autowired
    private SessionRepositoryFilter sessionRepositoryFilter;

    @Autowired
    private SecureProperties secureProperties;


//    @Bean
//    public HashcompareAuthenticationInterceptor authenticationInterceptor() {
//        return new HashcompareAuthenticationInterceptor();
//    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(hashcompareAuthenticationInterceptor()).addPathPatterns(billFilterAndInterceptorUrlPatterns(billProperties.getKeyOfHashCompareAuthenticationPathPrefix()));
        registry.addInterceptor(permissionInterceptor()).addPathPatterns(billFilterAndInterceptorUrlPatterns(secureProperties.getFilterUrl()));
    }

    @Bean
    public PermissionInterceptor permissionInterceptor() {
        return new PermissionInterceptor();
    }

    @Bean
    public HashcompareAuthenticationInterceptor hashcompareAuthenticationInterceptor(){
        return new HashcompareAuthenticationInterceptor();
    }

    @Bean
    public RoleInterceptor roleInterceptor() {
        return new RoleInterceptor();
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean1() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(wrapperFilter);
        registrationBean.addUrlPatterns(billFilterAndInterceptorUrlPatterns());
        registrationBean.setOrder(99);
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean2() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(sessionRepositoryFilter);
        registrationBean.addUrlPatterns(secureProperties.getFilterUrl());
        registrationBean.setOrder(98);
        return registrationBean;
    }

    private String billFilterAndInterceptorUrlPatterns() {
        String urlPatterns = billProperties.getKeyOfHashCompareAuthenticationPathPrefix();
        if (urlPatterns == null) {
            urlPatterns = "";
        }
        if(urlPatterns.endsWith("/*")){
            return urlPatterns;
        }
        if (urlPatterns.endsWith("/")) {
            urlPatterns += "*";
        } else {
            urlPatterns += "/*";
        }
        return urlPatterns;
    }

    private String billFilterAndInterceptorUrlPatterns(String urlPatterns) {
        if (urlPatterns == null) {
            urlPatterns = "";
        }
        if(urlPatterns.endsWith("/*")){
            return urlPatterns;
        }
        if (urlPatterns.endsWith("/")) {
            urlPatterns += "*";
        } else {
            urlPatterns += "/*";
        }
        return urlPatterns;
    }


    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.forEach((c) -> {
            if (c instanceof MappingJackson2HttpMessageConverter) {
                MappingJackson2HttpMessageConverter converter = (MappingJackson2HttpMessageConverter) c;
                ObjectMapper objectMapper = converter.getObjectMapper();
                objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                objectMapper.setDateFormat(formatter);
            }
        });

        super.extendMessageConverters(converters);
    }

}
