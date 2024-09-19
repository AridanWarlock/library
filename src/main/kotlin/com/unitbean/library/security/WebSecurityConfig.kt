package com.unitbean.library.security

import org.springframework.boot.convert.ApplicationConversionService.configure
import org.springframework.context.annotation.Configuration
//import org.springframework.security.config.annotation.web.builders.HttpSecurity
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity

//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfig() : WebSecurityConfig {
//    fun configure(http: HttpSecurity) {
//        http
//            .authorizeRequests()
//            .antMatchers("/your-endpoint").permitAll()  // Allow access to /your-endpoint
//            .anyRequest().authenticated()
//            .and()
//        // ... other configurations
//    }
//}