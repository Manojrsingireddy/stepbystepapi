package dev.manojrsingireddy.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@Configuration

public class AppConfig {
    @Value("${open.ai.key}")
     String openaiApiKey;

    @Bean
    public RestTemplate template(){
        RestTemplate restTemplate=new RestTemplate();
        return restTemplate;
    }
}
