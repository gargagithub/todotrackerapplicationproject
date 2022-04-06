package com.example.userauthentication;

//import com.example.userauthentication.filter.JwtFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@EnableEurekaClient
//@CrossOrigin(origins="http://localhost:4200/")
public class UserauthenticationApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserauthenticationApplication.class, args);
	}

//	@Bean
//	FilterRegistrationBean jwtFilter(){
//		FilterRegistrationBean filterRegistrationBean=new FilterRegistrationBean();
//		filterRegistrationBean.setFilter(new JwtFilter());
//		filterRegistrationBean.addUrlPatterns("/api/v1/userservice/*");
//		return filterRegistrationBean;
//	}

}


