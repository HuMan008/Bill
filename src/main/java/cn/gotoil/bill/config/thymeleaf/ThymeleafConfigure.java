package cn.gotoil.bill.config.thymeleaf;

import cn.gotoil.bill.ApplicationContextProvider;
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;


/**
 * Created by think on 2017/7/5.
 */
@Configuration
public class ThymeleafConfigure {


    @Bean
    public ITemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setTemplateMode("HTML5");
        templateResolver.setPrefix("classpath:/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setCharacterEncoding("utf-8");
        templateResolver.setOrder(1);
        templateResolver.setCacheable(false);
        templateResolver.setApplicationContext(ApplicationContextProvider.getApplicationContext());
        return templateResolver;
    }


    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setTemplateResolver(templateResolver());
        engine.addDialect(new SecureDialect());
        return engine;
    }

}
