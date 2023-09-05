package technical.test.api.services;

import java.time.Year;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Range;
import org.springframework.data.domain.Range.Bound;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.api.mappers.AuthorMapper;
import technical.test.api.mappers.BookMapper;
import technical.test.api.representations.AuthorRepresentation;
import technical.test.api.representations.BookRepresentation;
import technical.test.api.storage.models.Author;
import technical.test.api.storage.models.Book;
import technical.test.api.storage.repositories.AuthorRepository;
import technical.test.api.storage.repositories.BookRepository;

@Service
public class LibraryServiceImpl implements LibraryService {

  private final BookRepository bookRepository;

  private final AuthorRepository authorRepository;

  private final BookMapper bookMapper;

  private final AuthorMapper authorMapper;

  @Value("${library.release-dates.minimum-year:1800}")
  private Integer minimumYear;

  public LibraryServiceImpl(
      BookRepository bookRepository,
      AuthorRepository authorRepository,
      BookMapper bookMapper,
      AuthorMapper authorMapper) {
    this.bookRepository = bookRepository;
    this.authorRepository = authorRepository;
    this.bookMapper = bookMapper;
    this.authorMapper = authorMapper;
  }

  @Override
  public Mono<BookRepresentation> registerBook(
      String isbn, String title, Integer releaseDate, String authorId) {
    return bookRepository
        .save(
            Book.builder()
                .isbn(isbn)
                .title(title)
                .authorId(authorId)
                .releaseDate(releaseDate)
                .build())
        .map(bookMapper::toBookRepresentation);
  }

  @Override
  public Mono<AuthorRepresentation> registerAuthor(
      String firstName, String lastname, Integer birthDate) {
    return authorRepository
        .save(
            Author.builder()
                .id(
                    String.join(
                        "_", StringUtils.lowerCase(firstName), StringUtils.lowerCase(lastname)))
                .birthdate(birthDate)
                .firstname(firstName)
                .lastname(lastname)
                .build())
        .map(authorMapper::toAuthorRepresentation);
  }

  @Override
  public Flux<BookRepresentation> findAllBooks() {
    return bookRepository.findAll().map(bookMapper::toBookRepresentation);
  }

  @Override
  public Flux<BookRepresentation> findBookByAuthorAndDateBetween(
      String authorId, Integer startDate, Integer endDate) {
    Range releaseDateRange = buildReleaseDateRange(startDate, endDate);
    if (StringUtils.isNotEmpty(authorId)) {
      return bookRepository.findByAuthorIdAndReleaseDateBetween(authorId, releaseDateRange);
    }
    return bookRepository.findByReleaseDateBetween(releaseDateRange);
  }

  @Override
  public Flux<AuthorRepresentation> findAuthorByFirstnameAndLastnameAndBirthdate(
      String firstname, String lastname, Integer birthdate) {
    if (StringUtils.isEmpty(firstname)
        && StringUtils.isEmpty(lastname)
        && Objects.isNull(birthdate)) {
      return authorRepository.findAll().map(authorMapper::toAuthorRepresentation);
    }
    return authorRepository
        .findByFirstnameAndLastnameAndBirthdate(firstname, lastname, birthdate)
        .map(authorMapper::toAuthorRepresentation);
  }

  private Range<Integer> buildReleaseDateRange(Integer startDate, Integer endDate) {
    Range range =
        Range.from(Bound.inclusive(minimumYear)).to(Bound.inclusive(Year.now().getValue()));
    if (!Objects.isNull(startDate) && !Objects.isNull(endDate)) {
      range = Range.from(Bound.inclusive(startDate)).to(Bound.inclusive(endDate));
    } else if (Objects.isNull(startDate) && !Objects.isNull(endDate)) {
      range = Range.from(Bound.inclusive(minimumYear)).to(Bound.inclusive(endDate));
    } else if (!Objects.isNull(startDate)) {
      range = Range.from(Bound.inclusive(startDate)).to(Bound.inclusive(Year.now().getValue()));
    }
    return range;
  }
}
