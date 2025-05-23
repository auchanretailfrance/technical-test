package com.auchan.technicaltest;

import com.auchan.technicaltest.dto.AuthorDTO;
import com.auchan.technicaltest.dto.BookCreationDTO;
import com.auchan.technicaltest.dto.BookDTO; // Added this import
import com.auchan.technicaltest.facade.AuthorFacade;
import com.auchan.technicaltest.facade.BookFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class TestSupport {

    private final AuthorFacade authorFacade;
    private final BookFacade bookFacade;

    @Autowired
    public TestSupport(AuthorFacade authorFacade, BookFacade bookFacade) {
        this.authorFacade = authorFacade;
        this.bookFacade = bookFacade;
    }

    public Mono<Void> loadAuthorsAndBooks() {
        List<AuthorDTO> authors = List.of(
                new AuthorDTO("Isaac Asimov"),
                new AuthorDTO("Peter F. Hamilton"),
                new AuthorDTO("Michael Moorcock"),
                new AuthorDTO("Douglas Adams")
        );

        return Flux.fromIterable(authors)
                .flatMap(authorFacade::createAuthor) // This returns Flux<AuthorDTO>
                .then(loadBooksForAuthors()); // Changed thenMany to then, and it will return Mono<Void>
    }

    private Mono<Void> loadBooksForAuthors() { // Changed return type to Mono<Void>
        // Simplified: Assumes authors are created and identifiable by name.
        // A more robust approach would fetch created authors and use their IDs.
        List<BookCreationDTO> books = List.of(
                new BookCreationDTO("1234-5678-90", "Fondation", 1951, "Isaac Asimov"),
                new BookCreationDTO("1234-5678-91", "Fondation et Empire", 1952, "Isaac Asimov"),
                new BookCreationDTO("1234-5678-92", "Seconde Fondation", 1953, "Isaac Asimov"),
                new BookCreationDTO("1234-5678-93", "Fondation foudroyée", 1982, "Isaac Asimov"),
                new BookCreationDTO("1234-5678-94", "Terre et Fondation", 1986, "Isaac Asimov"),
                new BookCreationDTO("1234-5678-95", "Prélude à Fondation", 1988, "Isaac Asimov"),
                new BookCreationDTO("1234-5678-96", "L'Aube de Fondation", 1993, "Isaac Asimov"),
                new BookCreationDTO("2345-6789-01", "Les Robots", 1950, "Isaac Asimov"),
                new BookCreationDTO("2345-6789-02", "Un défilé de robots", 1964, "Isaac Asimov"),
                new BookCreationDTO("2345-6789-03", "Les Cavernes d'acier", 1954, "Isaac Asimov"),
                new BookCreationDTO("2345-6789-04", "Face aux feux du soleil", 1957, "Isaac Asimov"),
                new BookCreationDTO("2345-6789-05", "Les Robots de l'aube", 1983, "Isaac Asimov"),
                new BookCreationDTO("2345-6789-06", "Les Robots et l'Empire", 1985, "Isaac Asimov"),
                new BookCreationDTO("3456-7890-01", "Le Guide du voyageur galactique", 1979, "Douglas Adams")
        );

        return Flux.fromIterable(books)
                .flatMapSequential(bookFacade::createBook) // flatMapSequential to maintain order and handle Monos
                .then(); // Converts Flux<BookDTO> to Mono<Void>
    }

    // Convenience methods for individual tests if needed, though bulk loading is preferred for setup
    public Mono<AuthorDTO> registerAuthor(String name) {
        return authorFacade.createAuthor(new AuthorDTO(name));
    }

    public Mono<BookDTO> registerBook(String isbn, String title, int year, String authorName) {
        return bookFacade.createBook(new BookCreationDTO(isbn, title, year, authorName));
    }
}
