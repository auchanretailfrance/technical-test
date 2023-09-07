package technical.test.api.entities;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Data
@Document(collection = "books")
public class Book {
    @Id
    private String id;

    private String isbn;
    private String title;
    private int releaseDate;
    private String author;


}
