package cn.gotoil.bill.config;

import cn.gotoil.bill.config.property.BillProperties;
import cn.gotoil.bill.web.filter.HttpBodyStreamWrapperFilter;
import cn.gotoil.bill.web.interceptor.authentication.hashcompare.HashcompareAuthenticationInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.text.SimpleDateFormat;
import java.util.List;

@Configuration
@EnableWebMvc
public class BillWebMvcConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private BillProperties billProperties;

    @Autowired
    private HttpBodyStreamWrapperFilter wrapperFilter;

    @Autowired
    private HashcompareAuthenticationInterceptor hashcompareAuthenticationInterceptor;

//    @Bean
//    public HashcompareAuthenticationInterceptor authenticationInterceptor() {
//        return new HashcompareAuthenticationInterceptor();
//    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(hashcompareAuthenticationInterceptor);
    }


    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(wrapperFilter);
        String urlPatterns = billProperties.getKeyOfHashCompareAuthenticationPathPrefix();
        if (urlPatterns == null) {
            urlPatterns = "";
        }
        if (urlPatterns.endsWith("/")) {
            urlPatterns += "*";
        } else {
            urlPatterns += "/*";
        }

        registrationBean.addUrlPatterns(urlPatterns);
        registrationBean.setOrder(99);
        return registrationBean;
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
