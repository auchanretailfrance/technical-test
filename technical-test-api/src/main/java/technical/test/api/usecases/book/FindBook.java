package technical.test.api.usecases.book;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import technical.test.api.DTO.BookAuthorInformation;
import technical.test.api.repositories.book.IBookRepositoryImpl;
import technical.test.api.usecases.author.FindAuthor;

@Service
@AllArgsConstructor
public class FindBook {

  private final IBookRepositoryImpl bookRepositoryImpl;

  private FindAuthor findAuthor;

  public Flux<BookAuthorInformation> findBookByAuthorAndDateBetween(String authorId, Integer yearFrom, Integer yearTo) {
    return bookRepositoryImpl.findByAuthorIdAndReleaseDateYearIsBetween(authorId, yearFrom, yearTo)
        .flatMap(b -> findAuthor.findById(b.getAuthorId())
            .map(a -> {
              var authorName = a.isPresent() ? a.get().getLastName() : b.getAuthorId();
              return new BookAuthorInformation(b.getTitle(), b.getReleaseDateYear(), authorName);
            })
        );
  }
}
