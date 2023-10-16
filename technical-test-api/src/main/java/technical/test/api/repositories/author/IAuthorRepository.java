package technical.test.api.repositories.author;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import technical.test.api.entities.AuthorEntity;

@Repository
public interface IAuthorRepository extends ReactiveMongoRepository<AuthorEntity, String> {
}