package technical.test.api.representations;

import java.io.Serial;
import java.io.Serializable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Builder
@Getter
@Setter
public class BookRepresentation implements Serializable {

  @Serial private static final long serialVersionUID = -5822406190179321283L;

  @Id private String id;

  private String isbn;

  private String title;

  private Integer releaseDate;

  private String authorId;
}
