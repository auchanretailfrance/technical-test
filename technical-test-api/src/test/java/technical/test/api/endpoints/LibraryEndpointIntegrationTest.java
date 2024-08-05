package technical.test.api.endpoints;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.annotation.Resource;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import technical.test.api.TestSupport;
import technical.test.api.representations.AuthorRepresentation;
import technical.test.api.representations.BookRepresentation;
import technical.test.api.storage.repositories.AuthorRepository;
import technical.test.api.storage.repositories.BookRepository;

@AutoConfigureWebTestClient(timeout = "20000")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LibraryEndpointIntegrationTest {
  @Autowired private WebTestClient webTestClient;
  @Resource private BookRepository bookRepository;
  @Resource private AuthorRepository authorRepository;
  @Resource TestSupport testSupport;

  @BeforeEach
  public void load() {
    testSupport.loadBooks().then(testSupport.loadAuthor()).block();
  }

  @AfterEach
  public void clear() {
    bookRepository.deleteAll().block();
    authorRepository.deleteAll().block();
  }

  @Test
  public void given_author_should_add_entry_in_database() {
    // Given
    AuthorRepresentation author =
        AuthorRepresentation.builder()
            .firstname("Isaac")
            .lastname("Asimov")
            .birthdate(1920)
            .id("isaac_asimov")
            .build();

    // When
    List<AuthorRepresentation> authorRepresentationResponse =
        webTestClient
            .get()
            .uri(
                uri ->
                    uri.path("/library/authors")
                        .queryParam("firstname", "Isaac")
                        .queryParam("lastname", "Asimov")
                        .queryParam("birthdate", 1920)
                        .build())
            .exchange()
            .expectStatus()
            .isOk()
            .expectBodyList(AuthorRepresentation.class)
            .returnResult()
            .getResponseBody();

    // Then
    assertThat(authorRepresentationResponse).hasSize(1);
    AuthorRepresentation authorRepresentation = authorRepresentationResponse.iterator().next();
    assertThat(authorRepresentation.getId()).isEqualTo(author.getId());
    assertThat(authorRepresentation.getFirstname()).isEqualTo(author.getFirstname());
    assertThat(authorRepresentation.getLastname()).isEqualTo(author.getLastname());
    assertThat(authorRepresentation.getBirthdate()).isEqualTo(author.getBirthdate());
  }

  @Test
  public void given_book_should_add_entry_in_database() {
    // Given
    BookRepresentation book =
        BookRepresentation.builder()
            .isbn("1234-5678-90")
            .title("Fondation")
            .releaseDate(1951)
            .authorId("isaac_asimov")
            .build();

    // When
    final var bookRepresentationResponse =
        webTestClient
            .post()
            .uri(
                uri ->
                    uri.path("/library/books")
                        .queryParam("isbn", "1234-5678-90")
                        .queryParam("title", "Fondation")
                        .queryParam("releaseDateYear", 1951)
                        .queryParam("authorRefId", "isaac_asimov")
                        .build())
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(BookRepresentation.class)
            .returnResult()
            .getResponseBody();

    // Then
    assertThat(bookRepresentationResponse.getIsbn()).isEqualTo(book.getIsbn());
    assertThat(bookRepresentationResponse.getTitle()).isEqualTo(book.getTitle());
    assertThat(bookRepresentationResponse.getReleaseDate()).isEqualTo(book.getReleaseDate());
    assertThat(bookRepresentationResponse.getAuthorId()).isEqualTo(book.getAuthorId());
  }

  @Test
  public void given_books_in_database_should_return_all_books() {
    // when
    List<BookRepresentation> books =
        webTestClient
            .get()
            .uri(uri -> uri.path("/library/books").build())
            .exchange()
            .expectStatus()
            .isOk()
            .expectBodyList(BookRepresentation.class)
            .returnResult()
            .getResponseBody();

    Assertions.assertThat(books).hasSize(14);
  }

  @Test
  public void given_books_in_database_should_return_books_based_on_criteria() {
    // when
    List<BookRepresentation> books =
        webTestClient
            .get()
            .uri(
                uri ->
                    uri.path("/library/books")
                        .queryParam("authorRefId", "isaac_asimov")
                        .queryParam("yearFrom", "1970")
                        .queryParam("yearTo", "1990")
                        .build())
            .exchange()
            .expectStatus()
            .isOk()
            .expectBodyList(BookRepresentation.class)
            .returnResult()
            .getResponseBody();

    Assertions.assertThat(books).hasSize(5);
  }
}
