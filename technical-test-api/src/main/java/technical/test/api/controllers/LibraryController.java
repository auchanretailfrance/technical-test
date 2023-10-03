package technical.test.api.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.api.mappers.AuthorMapper;
import technical.test.api.mappers.BookMapper;
import technical.test.api.representations.AuthorRepresentation;
import technical.test.api.representations.BookRepresentation;
import technical.test.api.services.LibraryService;

@RestController
@RequestMapping("/library")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:8080")
public class LibraryController {

    private final LibraryService libraryService;

    private final BookMapper bookMapper;

    private final AuthorMapper authorMapper;

    @GetMapping("/books")
    public Flux<BookRepresentation> findBookByAuthorAndRange(@RequestParam(required = false) String authorRefId,
                                                 @RequestParam(required = false) Integer yearFrom,
                                                 @RequestParam(required = false) Integer yearTo)
    {
        return this.libraryService.findBookByAuthorAndDateBetween(authorRefId, yearFrom, yearTo)
                .map(bookMapper::bookToBookRepresentation);
    }

    @PostMapping("/books")
    public Mono<BookRepresentation> registerBook(@RequestParam(required = false) String isbn,
                                                 @RequestParam(required = false) String title,
                                                 @RequestParam(required = false) Integer releaseDateYear,
                                                 @RequestParam(required = false) String authorRefId)
    {
        return this.libraryService.registerBook(isbn, title, releaseDateYear, authorRefId)
                .map(bookMapper::bookToBookRepresentation);
    }

    @PostMapping("/authors")
    public Mono<AuthorRepresentation> registerAuthor(@RequestParam(required = false) String firstname,
                                                 @RequestParam(required = false) String lastname,
                                                 @RequestParam(required = false) Integer birthdate)
    {
        return this.libraryService.registerAuthor(firstname, lastname, birthdate)
                .map(authorMapper::authorToAuthorRepresentation);
    }

    @GetMapping("/authors")
    public Flux<AuthorRepresentation> getAllAuthors()
    {
        return this.libraryService.findAllAuthors()
                .map(authorMapper::authorToAuthorRepresentation);
    }
}
