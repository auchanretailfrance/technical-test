package technical.test.api.services;

import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;
import reactor.test.StepVerifier;
import technical.test.api.TestSupport;
import technical.test.api.repositories.book.IBookRepository;
import technical.test.api.usecases.author.RegisterAuthor;
import technical.test.api.usecases.book.FindBook;
import technical.test.api.usecases.book.RegisterBook;

@RunWith(SpringRunner.class)
@SpringBootTest
class LibraryServiceImplTest {

  final static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:5"));
  @Resource
  TestSupport testSupport;
  @Resource
  private RegisterAuthor registerAuthor;
  @Resource
  private RegisterBook registerBook;
  @Resource
  private FindBook findBook;
  @Resource
  private IBookRepository bookRepository;

  @BeforeAll
  static void prepare() {
    mongoDBContainer.start();
  }

  @AfterEach
  void clear() {
    bookRepository.deleteAll().block();
  }

  @Test
  void registerAuthor() {
    StepVerifier.create(registerAuthor.register("Isaac", "Asimov", 1920))
        .expectSubscription()
        .expectNextMatches(authorId -> authorId.getBirthDate() == 1920
            && authorId.getFirstName().equals("Isaac")
            && authorId.getLastName().equals("Asimov")
            && authorId.getId().equals("isaac_asimov"))
        .verifyComplete();
  }

  @Test
  void registerBook() {
    StepVerifier.create(registerBook.register("1234-5678-90", "Fondation", 1951, "isaac_asimov"))
        .expectSubscription()
        .expectNextMatches(book -> StringUtils.equals(book.isbn(), "1234-5678-90")
            && StringUtils.equals(book.title(), "Fondation")
            && book.releaseDateYear() == 1951
            && StringUtils.equals(book.authorId(), "isaac_asimov"))
        .verifyComplete();
  }

  @Test
  void findAllBooks() {
    StepVerifier.create(testSupport.loadBooks()
            .then(testSupport.loadAuthor())
            .thenMany(findBook.findBookByAuthorAndDateBetween(null, null, null)))
        .expectSubscription()
        .expectNextCount(14)
        .verifyComplete();
  }

  @Test
  void findBookByAuthor() {
    StepVerifier.create(testSupport.loadBooks()
            .then(testSupport.loadAuthor())
            .then(findBook.findBookByAuthorAndDateBetween("douglas_adams", null, null).collectList()))
        .expectSubscription()
        .assertNext(books -> {
              Assertions.assertThat(books).hasSize(1);
              Assertions.assertThat(books)
                  .extracting("title")
                  .containsExactly("Le Guide du voyageur galactique");
            }
        )
        .verifyComplete();
  }

  @Test
  void findBookByAuthorAndDateBefore() {
    StepVerifier.create(testSupport.loadBooks()
            .then(testSupport.loadAuthor())
            .then(findBook.findBookByAuthorAndDateBetween("isaac_asimov", null, 1959).collectList()))
        .expectSubscription()
        .assertNext(books -> {
              Assertions.assertThat(books).hasSize(6);
              Assertions.assertThat(books)
                  .extracting("title")
                  .containsExactlyInAnyOrder("Fondation", "Fondation et Empire", "Seconde Fondation",
                      "Les Robots", "Les Cavernes d'acier", "Face aux feux du soleil");
            }
        )
        .verifyComplete();
  }

  @Test
  void findBookByAuthorAndDateOver() {
    StepVerifier.create(testSupport.loadBooks()
            .then(testSupport.loadAuthor())
            .then(findBook.findBookByAuthorAndDateBetween("isaac_asimov", 1960, null).collectList()))
        .expectSubscription()
        .assertNext(books -> {
              Assertions.assertThat(books).hasSize(7);
              Assertions.assertThat(books)
                  .extracting("title")
                  .containsExactlyInAnyOrder("Fondation foudroyée",
                      "Terre et Fondation",
                      "Prélude à Fondation",
                      "L'Aube de Fondation",
                      "Un défilé de robots",
                      "Les Robots de l'aube",
                      "Les Robots et l'Empire");
            }
        )
        .verifyComplete();
  }

  @Test
  void findBookByDateBetween() {
    StepVerifier.create(testSupport.loadBooks()
            .then(testSupport.loadAuthor())
            .then(findBook.findBookByAuthorAndDateBetween(null, 1950, 1955).collectList()))
        .expectSubscription()
        .assertNext(books -> {
              Assertions.assertThat(books).hasSize(5);
              Assertions.assertThat(books)
                  .extracting("title")
                  .containsExactlyInAnyOrder("Fondation", "Fondation et Empire", "Seconde Fondation",
                      "Les Robots", "Les Cavernes d'acier");
            }
        )
        .verifyComplete();
  }
}
