package technical.test.api.storage.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "Books")
public class Book {

    @Id
    private String isbn;
    private String title;
    private Integer releaseDate;
    private String authorId;
}
