package cz.muni.fi.xgdovin.boot;

import cz.muni.fi.xgdovin.rest.config.RestConfiguration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.stream.Stream;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@Import(RestConfiguration.class)
@SpringBootApplication
/*@EnableAutoConfiguration(
        exclude = {
                DataSourceAutoConfiguration.class
                , ManagementWebSecurityAutoConfiguration.class
                , SecurityAutoConfiguration.class
        }
)*/
public class ExampleProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExampleProjectApplication.class);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry
                        .addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods(allowedMethods());
            }
        };
    }

    private String[] allowedMethods() {
        return Stream.of(GET, POST, PUT, DELETE, HEAD, OPTIONS)
                     .map(RequestMethod::name)
                     .toArray(String[]::new);
    }
}
