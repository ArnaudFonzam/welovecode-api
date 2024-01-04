package com.wlovec.welovecodeapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.wlovec.welovecodeapi.service.UserService;

@Configuration
public class ConfigurerPassWordEncoder {
	
	 // Password Encoding
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    protected void configureGlobal(AuthenticationManagerBuilder auth,  UserService userDetailsService) throws Exception {
        
        auth
             .userDetailsService(userDetailsService)
             .passwordEncoder(passwordEncoder());
   }
    
}
