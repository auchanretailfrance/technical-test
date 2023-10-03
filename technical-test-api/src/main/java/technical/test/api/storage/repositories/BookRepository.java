package technical.test.api.storage.repositories;

import org.springframework.data.domain.Range;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import technical.test.api.storage.models.Book;

@Repository
public interface BookRepository extends ReactiveMongoRepository<Book, String> {

    Flux<Book> findBookByAuthorIdAndReleaseDateBetween(String authorId, Range<Integer> releaseRange);
    Flux<Book> findBookByReleaseDateBetween(Range<Integer> releaseRange);

}
