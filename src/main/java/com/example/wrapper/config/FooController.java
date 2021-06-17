package com.example.wrapper.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.example.wrapper.services.TranslatorClient;

import feign.Client;
import feign.Contract;
import feign.Feign;
import feign.codec.Decoder;
import feign.codec.Encoder;

@Configuration
@Import(FeignClientsConfiguration.class)
public class FooController {

    private TranslatorClient translatorClient;
    
    Feign.Builder builder;

    //private FooClient adminClient;

        @Autowired
    public FooController(Decoder decoder, Encoder encoder, Client client, Contract contract) {
        this.builder = Feign.builder().client(client)
                .encoder(encoder)
                .decoder(decoder)
                .contract(contract);
                //.requestInterceptor(new BasicAuthRequestInterceptor("user", "user"))
                //.target(TranslatorClient.class, "http://translator-service");

		/*
		 * this.adminClient = Feign.builder().client(client) .encoder(encoder)
		 * .decoder(decoder) .contract(contract) .requestInterceptor(new
		 * BasicAuthRequestInterceptor("admin", "admin")) .target(FooClient.class,
		 * "https://PROD-SVC");
		 */
    }
        public void buildFeign(String url) {
            this.translatorClient = builder.target(TranslatorClient.class, url);
        }
        
		public TranslatorClient getTranslatorClient() {
			return translatorClient;
		}
		public void setTranslatorClient(TranslatorClient translatorClient) {
			this.translatorClient = translatorClient;
		}
}