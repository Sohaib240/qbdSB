package com.hourtimesheet.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

/**
 * Created by Abdus Salam on 9/2/2016.
 */

@Configuration
@PropertySource("classpath:thymeleaf.properties")
public class ThymeleafConfiguration {

    @Value("${templatePrefix}")
    private String templatePrefix;

    @Value("${templateMode}")
    private String templateMode;

    @Bean
    public ClassLoaderTemplateResolver templateResolver() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix(templatePrefix);
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(templateMode);
        return templateResolver;
    }

    @Bean
    public TemplateEngine templateEngine() {
        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        return templateEngine;
    }
}
