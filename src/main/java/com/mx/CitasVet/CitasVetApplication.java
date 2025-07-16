package com.mx.CitasVet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class CitasVetApplication {

	public static void main(String[] args) {
		SpringApplication.run(CitasVetApplication.class, args);
	}
	
	@Bean
	RestTemplate restTemplate() {
	    return new RestTemplate();
	}

}
