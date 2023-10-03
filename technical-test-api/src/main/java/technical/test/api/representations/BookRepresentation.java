package technical.test.api.representations;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookRepresentation {
    private String authorId;
    private String isbn;
    private String title;
    private Integer releaseDate;
}
