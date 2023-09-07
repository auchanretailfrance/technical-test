package technical.test.api.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class BookDTO {
    private String id;
    private String isbn;
    private String title;
    private int releaseDate;
    private String author;
}
