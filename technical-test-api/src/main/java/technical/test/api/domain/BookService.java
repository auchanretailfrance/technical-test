package technical.test.api.domain;

import jakarta.annotation.Nullable;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository repository;

    public Mono<Book> registerBook(@NonNull Book book) {
        return repository.registerAuthor(book);
    }

    public Flux<Book> retrieveAllBooks() {
        return repository.retrieveAllBooks();
    }

    public Flux<Book> retrieveBooksByCriteria(@Nullable String author, @Nullable Integer releaseDateGt, @Nullable Integer releaseDateLt) {
        return repository.retrieveBooksByCriteria(author, releaseDateGt, releaseDateLt);
    }

}
