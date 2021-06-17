package com.example.wrapper.controller;

import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.wrapper.config.FooController;
import com.example.wrapper.config.JsonConfig;
import com.example.wrapper.constant.WrapperConstants;
import com.example.wrapper.services.TranslatorClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;



@RestController
@RefreshScope
@RequestMapping("/wrapper-service")
public class WrapperController {
	
	private static final Logger LOG = LogManager.getLogger(WrapperController.class);
	
	 @Autowired
	 private EurekaClient eurekaClient;
	
	 @Autowired
	 private RestTemplate restTemplate;
	 

	 @Value("${config.value:Config Server is not working. Please check...}")
	 private String jsonConfigValue;
	 
	 @Value("${http.protocaol}")
	 private String httpProtocol;
	 
	 private String outputMessage = null;
	 
	 @Autowired
	 TranslatorClient translatorClient;
	 
	 @Autowired
	 FooController fooController;
	 
	@PostMapping(path = "/data")
	public String save(
			@RequestHeader MultiValueMap<String, String> headers,
			@RequestBody Object message) throws JsonProcessingException {
	 
		/*
		 * String url
		 * =getUrl(getTranslatorService(headers.getFirst(WrapperConstants.INPUT_TYPE),
		 * headers.getFirst(WrapperConstants.OUTPUT_TYPE)));
		 * 
		 * if (null!=url){ HttpEntity<Object> entityRequest=new
		 * HttpEntity<Object>(message,headers); outputMessage =
		 * restTemplate.postForObject(url, entityRequest, String.class);
		 * 
		 * System.out.println("Translating from : "+headers.getFirst(WrapperConstants.
		 * INPUT_TYPE)+"  to "+headers.getFirst(WrapperConstants.OUTPUT_TYPE)); }
		 */
		String url = httpProtocol+getTranslatorService(headers.getFirst(WrapperConstants.INPUT_TYPE),headers.getFirst(WrapperConstants.OUTPUT_TYPE));
		fooController.buildFeign(url);
		headers.remove("content-length");
		
		translatorClient =	fooController.getTranslatorClient();
		
		outputMessage = translatorClient.save(headers, message);
		return outputMessage;
	}
	
	
	@GetMapping("/hello")
	public String test() {
		LOG.log(Level.INFO, "/hello service call - &gt;");
		return "hello : ";
	}
	
	private String getUrl(String serviceName) {
		Application application = eurekaClient.getApplication(serviceName);
        InstanceInfo instanceInfo = application.getInstances().get(0);
        String url = "http://" + instanceInfo.getIPAddr() + ":" + instanceInfo.getPort() + "/translate/data";
        System.out.println("application :" + application);
        System.out.println("instanceInfo :" + instanceInfo);
        System.out.println("URL :" + url);
        return url;
	}
	 
	private String getTranslatorService(String input, String output) throws JsonMappingException, JsonProcessingException {
		 
		ObjectMapper objectMapper = new ObjectMapper();
		  List<JsonConfig> jsonConfigList =  objectMapper.readValue(jsonConfigValue, new TypeReference<List<JsonConfig>>(){});
		  JsonConfig jsonObject = jsonConfigList.stream()
				    .filter(j -> j.getInput().equalsIgnoreCase(input) && j.getOutput().equalsIgnoreCase(output))
				    .findFirst().orElse(null);
	     System.out.println("jsonObject.getService() :" + jsonObject.getService());
        return jsonObject.getService();
	}
}
