package technical.test.api.adapter.library;

import jakarta.annotation.Nullable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface LibraryService {
    Mono<BookRepresentation> registerBook(String isbn, String title, Integer releaseDate, String authorId);

    Mono<BookRepresentation> registerBookRepresentation(BookRepresentation book);

    Mono<AuthorRepresentation> registerAuthor(String firstname, String lastname, Integer birthdate);

    Mono<AuthorRepresentation> registerAuthorRepresentation(AuthorRepresentation author);

    Flux<BookRepresentation> findBookByAuthorAndDateBetween(@Nullable String author, @Nullable Integer releaseDateMin, @Nullable Integer releaseDateMinMax);

    Flux<BookRepresentation> findAllBooks();

    Flux<BookRepresentation> findBookRepresentationByAuthorAndDateBetween(@Nullable String author, @Nullable Integer releaseDateMin, @Nullable Integer releaseDateMinMax);
}
