package technical.test.api.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@AllArgsConstructor
public class AuthorEntity {

  private final String firstName;
  private final String lastName;
  private final int birthDate;
  @Id
  private String id;
}
