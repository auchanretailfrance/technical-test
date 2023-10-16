package technical.test.api.repositories.book;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.api.entities.BookEntity;

public interface IBookRepositoryImpl {

  Flux<BookEntity> findByAuthorIdAndReleaseDateYearIsBetween(String authorId, Integer yearFrom, Integer yearTo);

  Mono<BookEntity> insert(BookEntity bookEntity);
}
