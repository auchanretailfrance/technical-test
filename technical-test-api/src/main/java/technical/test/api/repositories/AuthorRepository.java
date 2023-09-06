package technical.test.api.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import technical.test.api.entities.Author;

@Repository
public interface AuthorRepository extends ReactiveMongoRepository<Author, String> {
}
