package technical.test.api.services;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.api.DTO.AuthorDTO;
import technical.test.api.DTO.BookDTO;
import technical.test.api.DTO.BookViewDTO;
import technical.test.api.entities.Author;
import technical.test.api.entities.Book;
import technical.test.api.mappers.AuthorMapper;
import technical.test.api.mappers.BookMapper;
import technical.test.api.repositories.AuthorRepository;
import technical.test.api.repositories.BookRepository;

@RequiredArgsConstructor
@Service
public class LibraryService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;


    // --------------------------------------
    // -------------- CREATE ----------------
    // --------------------------------------
    public Mono<AuthorDTO> registerAuthor(String firstName, String lastName, int birthYear){
        String id = firstName+"_"+lastName;
        return Mono.just(Author.builder().birthdate(birthYear).firstName(firstName).lastName(lastName).id(id.toLowerCase()).build())
                .flatMap(this.authorRepository::insert)
                .map(this.authorMapper::authorToAuthorDTO);
    }

    public Mono<BookDTO> registerBook(String isbn, String title, int releaseYear, String authorID){
        return Mono.just(Book.builder().author(authorID).isbn(isbn).title(title).releaseDate(releaseYear).build())
                .flatMap(this.bookRepository::insert)
                .map(this.bookMapper::bookToBookDTO);
    }

    // --------------------------------------
    // --------------- FIND -----------------
    // --------------------------------------


    public Flux<BookDTO> findAllBooks() {
        return this.bookRepository.findAll()
                .map(this.bookMapper::bookToBookDTO);
    }


    public Flux<AuthorDTO> findAllAuthors() {
        return this.authorRepository.findAll()
                .map(this.authorMapper::authorToAuthorDTO);
    }

    public Flux<BookViewDTO> findBookByAuthorAndDateBetween(String authorId, Integer fromYear, Integer toYear){
        Flux<Book> books;
        if( null != authorId){
            books = this.bookRepository.findBookByAuthorAndReleaseDateBetween(authorId,fromYear, toYear);
        }
        else{
            books = this.bookRepository.findBookByReleaseDateBetween(fromYear, toYear);
        }
        return books
                .flatMap(
                        book -> this.authorRepository.findById(book.getAuthor())
                                .map(author -> {
                                    String name = author.getFirstName() + " " + author.getLastName();
                                    return this.bookMapper.bookToBookViewDTO(book,name);
                                })

                );
    }

//    @PostConstruct()
//    void remplir() {
//        Mono.empty()
//                .then(this.registerBook("1234-5678-90","Fondation", 1951, "isaac_asimov"))
//                .then(this.registerBook("1234-5678-91","Fondation et Empire", 1952, "isaac_asimov"))
//                .then(this.registerBook("1234-5678-92","Seconde Fondation", 1953, "isaac_asimov"))
//                .then(this.registerBook("1234-5678-93","Fondation foudroyée", 1982, "isaac_asimov"))
//                .then(this.registerBook("1234-5678-94","Terre et Fondation", 1986, "isaac_asimov"))
//                .then(this.registerBook("1234-5678-95","Prélude à Fondation", 1988, "isaac_asimov"))
//                .then(this.registerBook("1234-5678-96","L'Aube de Fondation", 1993, "isaac_asimov"))
//                .then(this.registerBook("2345-6789-01", "Les Robots", 1950, "isaac_asimov"))
//                .then(this.registerBook("2345-6789-02", "Un défilé de robots", 1964, "isaac_asimov"))
//                .then(this.registerBook("2345-6789-03", "Les Cavernes d'acier", 1954, "isaac_asimov"))
//                .then(this.registerBook("2345-6789-04", "Face aux feux du soleil", 1957, "isaac_asimov"))
//                .then(this.registerBook("2345-6789-05", "Les Robots de l'aube", 1983, "isaac_asimov"))
//                .then(this.registerBook("2345-6789-06", "Les Robots et l'Empire", 1985, "isaac_asimov"))
//                .then(this.registerBook("3456-7890-01", "Le Guide du voyageur galactique", 1979, "douglas_adams"))
//                .then(this.registerAuthor("Isaac","Asimov", 1920))
//                .then(this.registerAuthor("Peter F.","Hamilton", 1960))
//                .then(this.registerAuthor("Michael","Moorcock", 1939))
//                .block();
//    }

}
