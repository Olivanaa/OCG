package br.com.fiap.oceanGuardian;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@Controller
@EnableCaching
@OpenAPIDefinition(
	info = @Info(
		title = "Ocean Guardian",
		version = "1.0",
		contact = @Contact(name = "ACGST", email = ""),
		summary = "API do app OceanGuardian - Proteção dos oceanos e do meio ambiente"
	)
)
public class OceanGuardianApplication {

	public static void main(String[] args) {
		SpringApplication.run(OceanGuardianApplication.class, args);
	}

	@RequestMapping
	@ResponseBody 
	public String home(){
		return "Ocean Guardian";
	}

}
