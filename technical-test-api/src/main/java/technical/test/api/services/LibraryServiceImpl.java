package technical.test.api.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.api.storage.models.Author;
import technical.test.api.storage.models.Book;
import technical.test.api.storage.repositories.AuthorRepository;
import technical.test.api.storage.repositories.BookRepository;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class LibraryServiceImpl implements LibraryService{

    @Autowired
    private final BookRepository bookRepository;

    @Autowired
    private final AuthorRepository authorRepository;

    @Override
    public Mono<Author> registerAuthor(String firstName, String lastName, Integer birthDate) {
        return authorRepository.save(
                    Author.builder()
                            .id(firstName.concat("_").concat(lastName).toLowerCase())
                            .firstname(firstName)
                            .lastname(lastName)
                            .birthdate(birthDate)
                            .build()
                );
    }

    @Override
    public Mono<Book> registerBook(String isbn, String title, Integer releaseDate, String authorId) {
        return bookRepository.save(Book.builder().isbn(isbn).title(title).releaseDate(releaseDate).authorId(authorId).build());
    }

    @Override
    public Flux<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Flux<Book> findBookByAuthorAndDateBetween(String authorId, Integer releaseDateMin, Integer releaseDateMax) {
        Integer rangeMin = releaseDateMin == null ? 0 : releaseDateMin;
        Integer rangeMax = releaseDateMax == null ? LocalDateTime.now().getYear() : releaseDateMax;

        Range<Integer> range = Range.from(Range.Bound.inclusive(rangeMin)).to(Range.Bound.inclusive(rangeMax));

        if(authorId == null){
            return bookRepository.findBookByReleaseDateBetween(range);
        }
        return bookRepository.findBookByAuthorIdAndReleaseDateBetween(authorId, range);
    }
}
