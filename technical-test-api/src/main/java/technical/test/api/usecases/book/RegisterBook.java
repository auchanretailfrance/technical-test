package technical.test.api.usecases.book;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import technical.test.api.DTO.Book;
import technical.test.api.entities.BookEntity;
import technical.test.api.mappers.IBookMapper;
import technical.test.api.repositories.book.IBookRepository;
import technical.test.api.repositories.book.IBookRepositoryImpl;

@Service
@AllArgsConstructor
public class RegisterBook {

  private final IBookRepositoryImpl bookRepository;

  private final IBookMapper bookMapper;

  public Mono<Book> register(String isbn, String title, int releaseDateYear, String authorId) {
    return Mono.just(new BookEntity(isbn, title, releaseDateYear, authorId))
        .flatMap(bookRepository::insert)
        .map(bookMapper::toBook);
  }
}
