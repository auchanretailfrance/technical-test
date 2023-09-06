package technical.test.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.api.DTO.*;
import technical.test.api.services.LibraryService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/library")
@CrossOrigin(origins = "http://localhost:8080")
public class LibraryController {
    private final LibraryService libraryService;

    @GetMapping("/getByAuthorAndDay")
    public Flux<BookViewDTO> findByAuthor(@RequestParam(required = false) String author,
                                          @RequestParam(required = false) Integer min,
                                          @RequestParam(required = false) Integer max)
    {
        return this.libraryService.findBookByAuthorAndDateBetween(author, min, max);
    }

    @PostMapping("/book")
    public Mono<BookDTO> registerBook(@RequestBody CreateBookDTO bookDTO)
    {
        return this.libraryService.registerBook(bookDTO.getIsbn(), bookDTO.getTitle(), bookDTO.getReleaseDate(), bookDTO.getAuthor());
    }

    @PostMapping("/author")
    public Mono<AuthorDTO> registerAuthor(@RequestBody CreateAuthorDTO authorDTO)
    {
        return this.libraryService.registerAuthor(authorDTO.getFirstName(), authorDTO.getLastName(), authorDTO.getBirthdate());
    }

    @GetMapping("/authors")
    public Flux<AuthorDTO> getAllAuthors()
    {
        return this.libraryService.findAllAuthors();
    }

}
