package technical.test.api.controllers;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.api.DTO.Book;
import technical.test.api.DTO.BookAuthorInformation;
import technical.test.api.usecases.book.FindBook;
import technical.test.api.usecases.book.RegisterBook;

@RestController
@RequestMapping(value = "/library/books")
@AllArgsConstructor
@Validated
public class BookController {

  private final FindBook findBook;

  private final RegisterBook registerBook;

  @GetMapping
  public Flux<BookAuthorInformation> findAllBooks(
      @RequestParam(value = "authorRefId", required = false)
      String authorId,
      @RequestParam(required = false)
      @Digits(integer = Integer.MAX_VALUE, fraction = 0, message = "yearFrom must be an integer")
      Integer yearFrom,
      @RequestParam(required = false)
      @Digits(integer = Integer.MAX_VALUE, fraction = 0, message = "yearTo must be an integer")
      Integer yearTo) {
    return findBook.findBookByAuthorAndDateBetween(authorId, yearFrom, yearTo);
  }

  @PostMapping
  public Mono<Book> registerBook(
      @RequestParam(required = false)
      @NotNull(message = "isbn can't be null")
      @NotBlank(message = "isbn can't be empty")
      String isbn,
      @RequestParam(required = false)
      @NotNull(message = "title can't be null")
      @NotBlank(message = "title can't be empty")
      String title,
      @RequestParam(required = false)
      @NotNull(message = "releaseDateYear can't be null")
      @Digits(integer = Integer.MAX_VALUE, fraction = 0, message = "releaseDateYear must be an integer")
      int releaseDateYear,
      @RequestParam(value = "authorRefId", required = false)
      @NotNull(message = "authorRefId can't be null")
      @NotBlank(message = "authorRefId can't be empty")
      String authorId) {
    return registerBook.register(isbn, title, releaseDateYear, authorId);
  }
}
