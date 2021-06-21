package com.example.wrapper.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.example.wrapper.config.FooController;

//@FeignClient(value = "translator-service", configuration = FooController.class)
@FeignClient(value = "xyz-service", configuration = FooController.class)
public interface TranslatorClient {
	
	// value = "translator-service"
	@RequestMapping(method = RequestMethod.POST, value = "/translate/data", consumes = "application/json")
	String save(@RequestHeader MultiValueMap<String, String> headers, @RequestBody Object message);

}
