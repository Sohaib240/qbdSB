package com.hourtimesheet.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by hassan on 2/5/17.
 */
@EnableWebMvc
@ComponentScan(basePackages = {"com.hourtimesheet.controller"})
@Configuration
public class WebRestfulConfiguration {
}
