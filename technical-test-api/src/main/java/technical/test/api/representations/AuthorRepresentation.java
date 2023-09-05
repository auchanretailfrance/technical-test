package technical.test.api.representations;

import java.io.Serial;
import java.io.Serializable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class AuthorRepresentation implements Serializable {

  @Serial private static final long serialVersionUID = -5828406190179381271L;

  private String id;

  private Integer birthdate;

  private String firstname;

  private String lastname;
}
