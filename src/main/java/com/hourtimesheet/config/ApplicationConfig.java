package com.hourtimesheet.config;

import com.ourtimesheet.config.EncryptedPropertySourcesPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * Created by Talha Zahid on 2/19/2016.
 */

@Configuration
public class ApplicationConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new EncryptedPropertySourcesPlaceholderConfigurer();
    }
}
