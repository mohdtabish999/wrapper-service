package com.example.wrapper.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ConfigBean  extends WebSecurityConfigurerAdapter {

	 @Bean
	    public RestTemplate getRestTemplate(){
	        return new RestTemplate();
	    }
	 
	  @Override
			protected void configure(HttpSecurity http) throws Exception {
				
				/*
				 * http.authorizeRequests(authz ->
				 * authz.antMatchers("/foos/**").hasAuthority("SCOPE_message.read")
				 * .antMatchers(HttpMethod.POST,
				 * "/foos").hasAuthority("SCOPE_message.write").anyRequest().authenticated())
				 * .oauth2ResourceServer(oauth2 -> oauth2.jwt());
				 */

				http.csrf().disable().authorizeRequests().antMatchers("/", "/error**").permitAll().anyRequest()
						.authenticated().and().oauth2ResourceServer().jwt();
					/*
					 * http .mvcMatcher("/foos/**") .authorizeRequests()
					 * .mvcMatchers("/foos/**").access("hasAuthority('SCOPE_message.read')") .and()
					 * .oauth2ResourceServer() .jwt();
					 */
			}
}
