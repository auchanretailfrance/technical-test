package technical.test.api.entities;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Data
@Document(collection = "authors")
public class Author {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private int birthdate;
}
