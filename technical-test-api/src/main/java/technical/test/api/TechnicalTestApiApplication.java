package technical.test.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@EnableReactiveMongoRepositories
@SpringBootApplication
public class TechnicalTestApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(TechnicalTestApiApplication.class, args);
  }
}
