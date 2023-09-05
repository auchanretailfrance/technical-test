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
public class Author {

  @Id private String id;

  private Integer birthdate;

  private String firstname;

  private String lastname;
}
