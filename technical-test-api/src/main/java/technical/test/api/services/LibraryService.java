package technical.test.api.services;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.api.representations.AuthorRepresentation;
import technical.test.api.representations.BookRepresentation;

public interface LibraryService {

  Mono<BookRepresentation> registerBook(
      String isbn, String title, Integer releaseDate, String authorId);

  Mono<AuthorRepresentation> registerAuthor(String firstName, String lastname, Integer birthDate);

  Flux<BookRepresentation> findAllBooks();

  Flux<BookRepresentation> findBookByAuthorAndDateBetween(
      String authorId, Integer startDate, Integer endDate);

  Flux<AuthorRepresentation> findAuthorByFirstnameAndLastnameAndBirthdate(
      String firstname, String lastname, Integer birthdate);
}
