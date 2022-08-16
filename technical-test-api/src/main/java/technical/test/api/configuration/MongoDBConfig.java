package technical.test.api.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackages="technical.test.api.repository")
@Configuration
public class MongoDBConfig {
}
