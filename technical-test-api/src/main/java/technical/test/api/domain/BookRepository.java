package technical.test.api.domain;

import jakarta.annotation.Nullable;
import lombok.NonNull;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BookRepository {
    Mono<Book> registerAuthor(@NonNull Book book);

    Flux<Book> retrieveAllBooks();

    Flux<Book> retrieveBooksByCriteria(@Nullable String author, @Nullable Integer releaseDateGt, @Nullable Integer releaseDateLt);
}
