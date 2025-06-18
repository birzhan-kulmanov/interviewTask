package com.interviewQuestion.testTask.configurations;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    // создаем Bean Model Mapper
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
