package com.geekbrains.demoboot.configuration;

import com.geekbrains.demoboot.services.ProductsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@ComponentScan("com.geekbrains.demoboot")
public class AppConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ProductsService productService(RestTemplate restTemplate) {
        return new ProductsService(restTemplate);
    }
}