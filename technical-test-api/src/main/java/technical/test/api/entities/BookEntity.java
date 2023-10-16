package technical.test.api.entities;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
public class BookEntity {

  private final String isbn;
  private final String title;
  private final Integer releaseDateYear;
  private final String authorId;
  @Id
  private String id;

  public BookEntity(String isbn, String title, Integer releaseDateYear, String authorId) {
    this.isbn = isbn;
    this.title = title;
    this.releaseDateYear = releaseDateYear;
    this.authorId = authorId;
  }
}
