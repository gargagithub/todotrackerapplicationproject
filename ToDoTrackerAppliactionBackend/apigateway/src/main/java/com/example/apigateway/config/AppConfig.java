package com.example.apigateway.config;

import com.example.apigateway.filter.JwtFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;

@Configuration
@CrossOrigin(origins = "*")
public class AppConfig {

    //ROUTESLOCATOR IS USED TO CONATIN THE INFORMATION ABOUT ROUTES.
    //roteslocatorbuilder is used to create routes
    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder)
    {
  return builder.routes()
          .route(p->p
                  .path("/api/v2/**")
                  .uri("http://localhost:8080/"))
          .route(p->p
                  .path("/api/v1/**")
                  .uri("http://localhost:8089"))
          .route(p->p
                  .path("/api/v3/**")
                  .uri("http://localhost:8082/"))
          .build();
    }

    @Bean
    FilterRegistrationBean jwtFilter(){
        FilterRegistrationBean filterRegistrationBean=new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new JwtFilter());
        filterRegistrationBean.addUrlPatterns("/api/v2/user/*");
        return filterRegistrationBean;
    }
}
