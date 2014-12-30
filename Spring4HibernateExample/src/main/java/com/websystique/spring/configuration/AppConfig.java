package com.websystique.spring.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.websystique.spring")
public class AppConfig {
/*
 * In our simple example, this class is empty and only reason for it’s existence 
 * is @ComponentScan which provides beans auto-detection facility. 
 * You may completely remove above configuration and 
 * put the component scan logic in application context level (in Main ). 
 */
}

