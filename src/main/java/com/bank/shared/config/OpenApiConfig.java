package com.bank.shared.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(info = @Info(title = "API de Emision De Tarjetas Bank", 
		description = "Emision De Tarjetas", version = "v1"), servers = {
        @Server(url = "${server.servlet.context-path}", description = "Generate server url"),
})

@Configuration
public class OpenApiConfig {
	
	@Bean
	GroupedOpenApi api() {
		return GroupedOpenApi.builder().group("api").pathsToMatch("/**").build();
	}
	
}
