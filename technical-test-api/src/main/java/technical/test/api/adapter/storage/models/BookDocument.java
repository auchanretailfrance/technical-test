package technical.test.api.adapter.storage.models;

import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Value
@Document
@FieldNameConstants
public class BookDocument {
    @Id
    String id;
    String isbn;
    String title;
    Integer releaseDate;
    String author;
}
