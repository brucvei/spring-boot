package com.bruna.demo.config;

import com.bruna.demo.domain.Boleto;
import com.bruna.demo.domain.Cartao;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class JacksonConfig {


	@Bean
	public Jackson2ObjectMapperBuilder objectMapperBuilder() {
		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder() {
			public void configure(ObjectMapper objectMapper) {
				objectMapper.registerSubtypes(Cartao.class);
				objectMapper.registerSubtypes(Boleto.class);
				super.configure(objectMapper);
			}
		};
		return builder;
	}
}
