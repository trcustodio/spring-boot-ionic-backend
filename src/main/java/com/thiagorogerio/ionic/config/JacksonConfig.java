package com.thiagorogerio.ionic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thiagorogerio.ionic.domain.PagamentoComBoleto;
import com.thiagorogerio.ionic.domain.PagamentoComCartao;

/**
 * 
 * @author trogerio
 *
 */

@Configuration 
public class JacksonConfig {
	
	//Consultar stackoverflow
	@Bean
	public Jackson2ObjectMapperBuilder objectMapperBuild() {
		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder() {
			public void configure(ObjectMapper objectMapper) {
				objectMapper.registerSubtypes(PagamentoComCartao.class);
				objectMapper.registerSubtypes(PagamentoComBoleto.class);
				super.configure(objectMapper);
			}
		};
		return builder;
	}

}