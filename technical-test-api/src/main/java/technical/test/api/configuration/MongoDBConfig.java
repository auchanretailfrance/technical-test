package technical.test.api.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.index.MongoPersistentEntityIndexResolver;
import org.springframework.data.mongodb.core.mapping.BasicMongoPersistentEntity;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "technical.test.api.repository")
public class MongoDBConfig {

	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private MongoConverter mongoConverter;

	@EventListener(ApplicationReadyEvent.class)
	public void initIndicesAfterStartup() {
		var mappingContext = this.mongoConverter.getMappingContext();
		if (mappingContext instanceof MongoMappingContext) {
			var mongoMappingContext = (MongoMappingContext) mappingContext;
			for (BasicMongoPersistentEntity<?> persistentEntity : mongoMappingContext.getPersistentEntities()) {
				var clazz = persistentEntity.getType();
				if (clazz.isAnnotationPresent(Document.class)) {
					var indexOps = mongoTemplate.indexOps(clazz);
					var resolver = new MongoPersistentEntityIndexResolver(mongoMappingContext);
					resolver.resolveIndexFor(clazz).forEach(indexOps::ensureIndex);
				}
			}
		}
	}
}
