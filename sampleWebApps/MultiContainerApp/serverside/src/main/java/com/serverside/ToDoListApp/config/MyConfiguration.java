package com.serverside.ToDoListApp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyConfiguration implements WebMvcConfigurer {
	
    @Override
    public void addCorsMappings(CorsRegistry registry) {		
        registry.addMapping("/**")		//allow all origins
                .allowedMethods("*");		//allow all methods
    }

}
