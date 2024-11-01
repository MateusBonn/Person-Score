package com.mateus_bonn.pessoa_score;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Pessoa Score", version = "1", description = "API para cadastro de pessoa com score"))
public class PessoaScoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(PessoaScoreApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
