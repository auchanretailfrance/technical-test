package technical.test.api.endpoints;


import jakarta.annotation.Resource;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import technical.test.api.DTO.Author;
import technical.test.api.DTO.Book;
import technical.test.api.TestSupport;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@AutoConfigureWebTestClient(timeout = "20000")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LibraryEndpointIntegrationTest {
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private ReactiveMongoOperations reactiveMongoOperations;

    @Resource
    TestSupport testSupport;

    @Before
    public void cleanup() {
        reactiveMongoOperations.dropCollection(Book.class).block();
        reactiveMongoOperations.dropCollection(Author.class).block();

        testSupport.loadAuthor().then(testSupport.loadBooks()).block();
    }

    @Test
    public void given_author_should_add_entry_in_database() {
        // Given
        Author author = new Author("isaac", "asimov", 1920);

        // When
        final var authorRepresentationResponse = webTestClient
                .post()
                .uri(uri -> uri.path("/library/authors")
                        .queryParam("firstname", "isaac")
                        .queryParam("lastname", "asimov")
                        .queryParam("birthdate", "1920")
                        .build()
                ).exchange()
                .expectStatus()
                .isOk()
                .expectBody(Author.class)
                .returnResult()
                .getResponseBody();

        // Then
        assertThat(authorRepresentationResponse).isEqualTo(author);
    }

    @Test
    public void given_book_should_add_entry_in_database() {
        // Given
        Book book = new Book("1234-5678-90", "Fondation", 1951, "isaac_asimov");

        // When
        final var bookRepresentationResponse = webTestClient
                .post()
                .uri(uri -> uri.path("/library/books")
                        .queryParam("isbn", "1234-5678-90")
                        .queryParam("title", "Fondation")
                        .queryParam("releaseDateYear", "1951")
                        .queryParam("authorRefId", "isaac_asimov")
                        .build()
                ).exchange()
                .expectStatus()
                .isOk()
                .expectBody(Book.class)
                .returnResult()
                .getResponseBody();

        // Then
        assertThat(bookRepresentationResponse).isEqualTo(book);
    }

    @Test
    public void given_books_in_database_should_return_all_books() {
        // when
        List<Book> books = webTestClient
                .get()
                .uri(uri -> uri.path("/library/books")
                        .build()
                ).exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Book.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertThat(books).hasSize(14);
    }

    @Test
    public void given_books_in_database_should_return_books_based_on_criteria() {
        // when
        List<Book> books = webTestClient
                .get()
                .uri(uri -> uri.path("/library/books")
                        .queryParam("authorRefId", "isaac_asimov")
                        .queryParam("yearFrom", "1970")
                        .queryParam("yearTo", "1990")
                        .build()
                ).exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Book.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertThat(books).hasSize(5);
    }
}
