package technical.test.api.adapter.library;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.api.adapter.library.mapper.AuthorMapper;
import technical.test.api.adapter.library.mapper.BookMapper;
import technical.test.api.domain.Author;
import technical.test.api.domain.AuthorService;
import technical.test.api.domain.Book;
import technical.test.api.domain.BookService;

@Service
@RequiredArgsConstructor
public class LibraryServiceImpl implements LibraryService {
    private final BookMapper bookMapper;
    private final BookService bookService;
    private final AuthorMapper authorMapper;
    private final AuthorService authorService;

    @Override
    public Mono<BookRepresentation> registerBook(String isbn, String title, Integer releaseDate, String authorId) {
        return bookService.registerBook(Book.of(isbn, title, releaseDate, authorId))
                .map(bookMapper::map);
    }

    @Override
    public Mono<BookRepresentation> registerBookRepresentation(BookRepresentation book) {
        return bookService.registerBook(bookMapper.map(book))
                .map(bookMapper::map);
    }

    @Override
    public Mono<AuthorRepresentation> registerAuthor(String firstname, String lastname, Integer birthdate) {
        return authorService.registerAuthor(Author.of(firstname, lastname, birthdate))
                .map(authorMapper::map);
    }

    @Override
    public Mono<AuthorRepresentation> registerAuthorRepresentation(AuthorRepresentation author) {
        return authorService.registerAuthor(authorMapper.map(author))
                .map(authorMapper::map);
    }

    @Override
    public Flux<BookRepresentation> findBookByAuthorAndDateBetween(String author, Integer releaseDateMin, Integer releaseDateMax) {
        return bookService.retrieveBooksByCriteria(author, releaseDateMin, releaseDateMax)
                .map(bookMapper::map);
    }

    @Override
    public Flux<BookRepresentation> findAllBooks() {
        return bookService.retrieveAllBooks()
                .map(bookMapper::map);
    }

    @Override
    public Flux<BookRepresentation> findBookRepresentationByAuthorAndDateBetween(String author, Integer releaseDateMin, Integer releaseDateMax) {
        return findBookByAuthorAndDateBetween(author, releaseDateMin, releaseDateMax);
    }
}
