package com.auchan.technicaltest.endpoints;

import com.auchan.technicaltest.TestSupport;
import com.auchan.technicaltest.dto.AuthorDTO;
import com.auchan.technicaltest.dto.BookCreationDTO;
import com.auchan.technicaltest.dto.BookDTO;
import com.auchan.technicaltest.model.Author;
import com.auchan.technicaltest.model.Book;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@Testcontainers
@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient(timeout = "30000")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EndpointIntegrationTest {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:5"));

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ReactiveMongoOperations reactiveMongoOperations;

    @Resource
    TestSupport testSupport;

    @BeforeEach
    public void cleanup() {
        // Ensure collections are dropped and test data is loaded before each test
        reactiveMongoOperations.dropCollection(Book.class)
                .then(reactiveMongoOperations.dropCollection(Author.class))
                .then(testSupport.loadAuthorsAndBooks())
                .block();
    }

    @Test
    public void should_create_author_and_retrieve_it() {
        AuthorDTO newAuthor = new AuthorDTO("George Orwell");

        AuthorDTO createdAuthor = webTestClient
                .post()
                .uri("/api/v1/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(newAuthor), AuthorDTO.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(AuthorDTO.class)
                .value(author -> assertThat(author.name()).isEqualTo("George Orwell"))
                .returnResult()
                .getResponseBody();

        assertThat(createdAuthor).isNotNull();

        // Retrieve all authors to verify inclusion
        webTestClient
                .get()
                .uri("/api/v1/authors")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(AuthorDTO.class)
                .value(authors -> {
                    assertThat(authors).extracting(AuthorDTO::name).contains("Isaac Asimov", "Douglas Adams", "George Orwell");
                });
    }

    @Test
    public void should_create_book_and_retrieve_it() {
        // Assuming "George Orwell" was created by loadAuthorsAndBooks or another test setup.
        // For robustness, ensure author exists or create here.
        // Let's use an existing author from TestSupport for simplicity
        String authorName = "Isaac Asimov";
        BookCreationDTO newBook = new BookCreationDTO("978-0451524935", "1984", 1949, authorName);

        BookDTO createdBook = webTestClient
                .post()
                .uri("/api/v1/books")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(newBook), BookCreationDTO.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(BookDTO.class)
                .value(book -> {
                    assertThat(book.title()).isEqualTo("1984");
                    assertThat(book.authorName()).isEqualTo(authorName);
                    assertThat(book.publicationYear()).isEqualTo(1949);
                })
                .returnResult()
                .getResponseBody();

        assertThat(createdBook).isNotNull();
    }

    @Test
    public void should_return_all_books_when_no_criteria() {
        // Data is pre-loaded by @BeforeEach's call to testSupport.loadAuthorsAndBooks()
        webTestClient
                .get()
                .uri("/api/v1/books")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(BookDTO.class)
                .value(books -> assertThat(books).hasSize(14)); // From TestSupport
    }

    @Test
    public void should_return_books_by_author_name() {
        webTestClient
                .get()
                .uri(uriBuilder -> uriBuilder.path("/api/v1/books")
                        .queryParam("authorName", "Isaac Asimov")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(BookDTO.class)
                .value(books -> {
                    assertThat(books).isNotEmpty();
                    assertThat(books).allMatch(book -> book.authorName().equals("Isaac Asimov"));
                    // Count based on TestSupport data for Isaac Asimov
                    assertThat(books).hasSize(13);
                });
    }

    @Test
    public void should_return_books_by_publication_year() {
        webTestClient
                .get()
                .uri(uriBuilder -> uriBuilder.path("/api/v1/books")
                        .queryParam("publicationYear", "1951")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(BookDTO.class)
                .value(books -> {
                    assertThat(books).isNotEmpty();
                    assertThat(books).allMatch(book -> book.publicationYear() == 1951);
                    // Count based on TestSupport data for year 1951
                    assertThat(books).hasSize(1);
                    assertThat(books.get(0).title()).isEqualTo("Fondation");
                });
    }

    @Test
    public void should_return_books_by_author_name_and_publication_year() {
        webTestClient
                .get()
                .uri(uriBuilder -> uriBuilder.path("/api/v1/books")
                        .queryParam("authorName", "Isaac Asimov")
                        .queryParam("publicationYear", "1951")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(BookDTO.class)
                .value(books -> {
                    assertThat(books).hasSize(1);
                    BookDTO book = books.get(0);
                    assertThat(book.authorName()).isEqualTo("Isaac Asimov");
                    assertThat(book.publicationYear()).isEqualTo(1951);
                    assertThat(book.title()).isEqualTo("Fondation");
                });
    }
}
