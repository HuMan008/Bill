package cn.gotoil.bill.config;

import cn.gotoil.bill.config.property.BillProperties;
import cn.gotoil.bill.config.property.SecureProperties;
import cn.gotoil.bill.web.filter.HttpBodyStreamWrapperFilter;
import cn.gotoil.bill.web.interceptor.authentication.hashcompare.HashcompareAuthenticationInterceptor;
import cn.gotoil.bill.web.interceptor.authentication.permissioncompare.BillWebAuthenticationInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.base.Charsets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
//import org.springframework.session.web.http.SessionRepositoryFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.text.SimpleDateFormat;
import java.util.List;

@Configuration

public class BillWebMvcConfig extends WebMvcConfigurationSupport {
    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
            "classpath:/META-INF/resources/", "classpath:/resources/",
            "classpath:/static/", "classpath:/public/"};


    @SuppressWarnings("all")
    @Autowired
    private BillProperties billProperties;

    @Autowired
    @SuppressWarnings("all")
    private HttpBodyStreamWrapperFilter wrapperFilter;


    @Autowired
    @SuppressWarnings("all")
    private SecureProperties secureProperties;

  /*  @Autowired
    @SuppressWarnings("all")
    private SessionRepositoryFilter sessionRepositoryFilter;*/

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(hashcompareAuthenticationInterceptor()).addPathPatterns(billFilterAndInterceptorUrlPatterns(billProperties.getKeyOfHashCompareAuthenticationPathPrefix()));
        registry.addInterceptor(billWebAuthenticationInterceptor()).addPathPatterns(billFilterAndInterceptorUrlPatterns(secureProperties.getFilterUrl()));
    }

    @Bean
    public BillWebAuthenticationInterceptor billWebAuthenticationInterceptor() {
        return new BillWebAuthenticationInterceptor();
    }

    @Bean
    public HashcompareAuthenticationInterceptor hashcompareAuthenticationInterceptor() {
        return new HashcompareAuthenticationInterceptor();
    }


    @Bean
    public FilterRegistrationBean filterRegistrationBean1() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(wrapperFilter);
        registrationBean.addUrlPatterns(billFilterAndInterceptorUrlPatterns(billProperties.getKeyOfHashCompareAuthenticationPathPrefix()));
        registrationBean.setOrder(99);
        return registrationBean;
    }

   /* @Bean
    public FilterRegistrationBean sessionRepositoryFilterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(sessionRepositoryFilter);
        registrationBean.addUrlPatterns(secureProperties.getRedisSessionFilterUlr());
        registrationBean.setOrder(Integer.MIN_VALUE+5);
        return registrationBean;
    }
*/

    /*private String billFilterAndInterceptorUrlPatterns() {
        String urlPatterns = billProperties.getKeyOfHashCompareAuthenticationPathPrefix();
        if (urlPatterns == null) {
            urlPatterns = "";
        }
        if (urlPatterns.endsWith("/*")) {
            return urlPatterns;
        }
        if (urlPatterns.endsWith("/")) {
            urlPatterns += "*";
        } else {
            urlPatterns += "/*";
        }
        return urlPatterns;
    }*/

    private String billFilterAndInterceptorUrlPatterns(String urlPatterns) {
        if (urlPatterns == null) {
            urlPatterns = "";
        }
        if (urlPatterns.endsWith("/*") ||urlPatterns.endsWith("/**")) {
            return urlPatterns;
        }
        if (urlPatterns.endsWith("/")) {
            urlPatterns += "**";
        } else {
            urlPatterns += "/**";
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

        converters.add(responseBodyConverter());

        super.extendMessageConverters(converters);
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/swagger-ui.html").addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS[0]);
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/static/**").addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
    }


    @Bean
    public HttpMessageConverter<String> responseBodyConverter() {
        StringHttpMessageConverter converter = new StringHttpMessageConverter(
                Charsets.UTF_8);
        return converter;
    }


}
