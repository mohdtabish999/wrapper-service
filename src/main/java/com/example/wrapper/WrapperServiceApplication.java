package com.example.wrapper;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.wrapper.config.JsonConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class WrapperServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WrapperServiceApplication.class, args);
	}

	@RefreshScope
	@RestController
	class MessageRestController {

	  @Value("${message:Config Server is not working. Please check...}")
	  private String message;

	  @RequestMapping("/message")
	  String getMessage() throws JsonMappingException, JsonProcessingException {
		  ObjectMapper objectMapper = new ObjectMapper();
		  List<JsonConfig> json =  objectMapper.readValue(message, new TypeReference<List<JsonConfig>>(){});
		  System.out.println(json);
	    return this.message;
	  }
	}
}
