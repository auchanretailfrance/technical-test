package technical.test.api.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateBookDTO {
    private String isbn;
    private String title;
    private int releaseDate;
    private String author;
}
