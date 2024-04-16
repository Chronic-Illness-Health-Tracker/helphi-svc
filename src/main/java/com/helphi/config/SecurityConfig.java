package com.helphi.config;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

    @Configuration
    //@EnableWebSecurity
    public class SecurityConfig {

//        @Bean
//        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//            http.authorizeHttpRequests(auth -> {
//                auth.requestMatchers("/user").permitAll();
//                auth.requestMatchers("/v3/api-docs").permitAll();
//                auth.anyRequest().authenticated();
//            }).cors(Customizer.withDefaults())
//                    .oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()));
////                    .csrf((csrf) -> csrf
////                            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
////                            .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler()))
////                    .addFilterAfter(new CookieCsrfFilter(), BasicAuthenticationFilter.class);
//
//
//            return http.build();
//        }

    @Bean
    public WebMvcConfigurer corsConfig() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:4200")
                        .allowedMethods(HttpMethod.GET.name(),
                                HttpMethod.PUT.name(),
                                HttpMethod.POST.name(),
                                HttpMethod.DELETE.name())
                        .allowedHeaders(HttpHeaders.CONTENT_TYPE, HttpHeaders.AUTHORIZATION);
            }
        };
    }
    }

