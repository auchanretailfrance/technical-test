package com.auchan.technicaltest.services;

import com.auchan.technicaltest.TestSupport;
import com.auchan.technicaltest.model.Author;
import com.auchan.technicaltest.model.Book;
import com.auchan.technicaltest.repository.AuthorRepository;
import com.auchan.technicaltest.repository.BookRepository;
import com.auchan.technicaltest.service.AuthorService;
import com.auchan.technicaltest.service.BookService;
import jakarta.annotation.Resource;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Testcontainers // Added for Testcontainers support
class ServiceIntegrationTest { // Renamed from LibraryServiceImplTest

    @Container // Define and manage MongoDB container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:5"));

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Autowired
    private AuthorService authorService;

    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @Resource
    TestSupport testSupport; // Reused for data loading consistency

    // Removed manual Testcontainers setup from static block

    @BeforeEach
    void setupTestData() {
        // Clean and load data using TestSupport for consistency with endpoint tests
        // Testcontainers will ensure a clean DB for each test class if managed by @Container
        // For @BeforeEach, manual cleanup might still be desired if tests within the same class need isolation beyond what Testcontainers provides by default per-class
        authorRepository.deleteAll()
                .then(bookRepository.deleteAll())
                .then(testSupport.loadAuthorsAndBooks()) // This method now handles both authors and books
                .block();
    }

    @AfterEach
    void clear() {
        authorRepository.deleteAll().block();
        bookRepository.deleteAll().block();
    }

    @Test
    void createAuthorAndVerify() {
        Author newAuthor = new Author("George Orwell");
        StepVerifier.create(authorService.createAuthor(newAuthor))
                .expectSubscription()
                .assertNext(author -> {
                    Assertions.assertThat(author.getId()).isNotNull();
                    Assertions.assertThat(author.getName()).isEqualTo("George Orwell");
                })
                .verifyComplete();
    }

    @Test
    void createBookAndVerify() {
        // First, ensure an author exists to link the book to.
        Author authorToLink = authorService.createAuthor(new Author("Test Author For Book")).block();
        Assertions.assertThat(authorToLink).isNotNull();
        Assertions.assertThat(authorToLink.getId()).isNotNull();

        Book newBook = new Book("9999-9999-99", "Test Book Title", 2024, authorToLink);

        StepVerifier.create(bookService.createBook(newBook))
                .expectSubscription()
                .assertNext(book -> {
                    Assertions.assertThat(book.getIsbn()).isEqualTo("9999-9999-99");
                    Assertions.assertThat(book.getTitle()).isEqualTo("Test Book Title");
                    Assertions.assertThat(book.getPublicationYear()).isEqualTo(2024);
                    Assertions.assertThat(book.getAuthor()).isNotNull();
                    Assertions.assertThat(book.getAuthor().getId()).isEqualTo(authorToLink.getId());
                })
                .verifyComplete();
    }

    @Test
    void findAllBooksLoadedByTestSupport() {
        StepVerifier.create(bookService.getAllBooks().collectList())
                .expectSubscription()
                .assertNext(books -> Assertions.assertThat(books).hasSize(14)) // From TestSupport data
                .verifyComplete();
    }
    
    @Test
    void findBooksByAuthorName() {
        StepVerifier.create(bookService.getBooksByAuthorName("Isaac Asimov").collectList())
                .expectSubscription()
                .assertNext(books -> {
                    Assertions.assertThat(books).hasSize(13); // Based on TestSupport data
                    Assertions.assertThat(books).allMatch(book -> book.getAuthor().getName().equals("Isaac Asimov"));
                })
                .verifyComplete();
    }

    @Test
    void findBooksByPublicationYear() {
        StepVerifier.create(bookService.getBooksByPublicationYear(1951).collectList())
                .expectSubscription()
                .assertNext(books -> {
                    Assertions.assertThat(books).hasSize(1); // Fondation by Asimov
                    Assertions.assertThat(books.get(0).getTitle()).isEqualTo("Fondation");
                })
                .verifyComplete();
    }

    @Test
    void findBooksByAuthorNameAndPublicationYear() {
        StepVerifier.create(bookService.getBooksByAuthorNameAndPublicationYear("Isaac Asimov", 1951).collectList())
                .expectSubscription()
                .assertNext(books -> {
                    Assertions.assertThat(books).hasSize(1);
                    Book book = books.get(0);
                    Assertions.assertThat(book.getAuthor().getName()).isEqualTo("Isaac Asimov");
                    Assertions.assertThat(book.getPublicationYear()).isEqualTo(1951);
                    Assertions.assertThat(book.getTitle()).isEqualTo("Fondation");
                })
                .verifyComplete();
    }
}
