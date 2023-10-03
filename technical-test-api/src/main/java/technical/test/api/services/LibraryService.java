package technical.test.api.services;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.api.storage.models.Author;
import technical.test.api.storage.models.Book;

public interface LibraryService {

    Mono<Author> registerAuthor(String firstName, String lastName, Integer birthDate);
    Mono<Book> registerBook(String isbn, String title, Integer releaseDate, String authorId);
    Flux<Book> findAllBooks();
    Flux<Author> findAllAuthors();
    Flux<Book> findBookByAuthorAndDateBetween(String authorId, Integer releaseDateMin, Integer releaseDateMax);

}
