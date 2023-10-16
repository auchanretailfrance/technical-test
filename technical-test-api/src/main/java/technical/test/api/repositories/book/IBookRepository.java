package technical.test.api.repositories.book;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import technical.test.api.entities.BookEntity;

@Repository
public interface IBookRepository extends ReactiveMongoRepository<BookEntity, String> {
}