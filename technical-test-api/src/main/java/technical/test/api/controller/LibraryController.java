package technical.test.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.api.representations.AuthorRepresentation;
import technical.test.api.representations.BookRepresentation;
import technical.test.api.services.LibraryService;

@CrossOrigin(origins = "http://localhost:8080")
@RequiredArgsConstructor
@RequestMapping("/library")
@RestController
public class LibraryController {

  private final LibraryService libraryService;

  @GetMapping("/authors")
  public Flux<AuthorRepresentation> findAuthors(
      @RequestParam(name = "firstname", required = false) final String firstname,
      @RequestParam(name = "lastname", required = false) final String lastname,
      @RequestParam(name = "birthdate", required = false) final Integer birthdate) {
    return libraryService.findAuthorByFirstnameAndLastnameAndBirthdate(
        firstname, lastname, birthdate);
  }

  @GetMapping("/books")
  public Flux<BookRepresentation> filterBooks(
      @RequestParam(name = "authorRefId", required = false) final String authorRefId,
      @RequestParam(name = "yearFrom", required = false) final Integer yearFrom,
      @RequestParam(name = "yearTo", required = false) final Integer yearTo) {
    return libraryService.findBookByAuthorAndDateBetween(authorRefId, yearFrom, yearTo);
  }

  @PostMapping("/books")
  public Mono<BookRepresentation> filterBooks(
      @RequestParam(name = "isbn") final String isbn,
      @RequestParam(name = "title") final String title,
      @RequestParam(name = "releaseDateYear") final Integer releaseDateYear,
      @RequestParam(name = "authorRefId") final String authorRefId) {
    return libraryService.registerBook(isbn, title, releaseDateYear, authorRefId);
  }
}
