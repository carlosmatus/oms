package com.demo.oms.config;

import com.demo.oms.service.CommerceToolService;
import com.demo.oms.service.CommerceToolServiceMockImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public CommerceToolService commerceToolService() {
        return new CommerceToolServiceMockImpl(); // Or any necessary configuration
    }
}
