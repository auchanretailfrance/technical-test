package technical.test.api.storage.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Getter
@Setter
@Document
public class Book {

  @Id private String id;

  private String isbn;

  private String title;

  private Integer releaseDate;

  private String authorId;
}
