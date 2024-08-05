package technical.test.api.storage.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import technical.test.api.storage.models.Author;

@Repository
public interface AuthorRepository extends ReactiveMongoRepository<Author, String> {

  Flux<Author> findByFirstnameAndLastnameAndBirthdate(
      String firstname, String lastname, Integer birthdate);
}
