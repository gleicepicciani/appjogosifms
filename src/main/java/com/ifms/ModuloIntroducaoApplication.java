package com.ifms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//podemos mapear a partir do protocolo http
//@RestController
public class ModuloIntroducaoApplication {
	
	public static void main(String[] args) {
		//run sobe a aplicacao
		SpringApplication.run(ModuloIntroducaoApplication.class, args);
	}
}
