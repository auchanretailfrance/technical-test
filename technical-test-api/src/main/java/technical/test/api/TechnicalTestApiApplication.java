package technical.test.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.web.reactive.config.EnableWebFlux;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableWebFlux
        @EnableAutoConfiguration(exclude = {WebMvcAutoConfiguration.class })
public class TechnicalTestApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TechnicalTestApiApplication.class, args);
    }

}
