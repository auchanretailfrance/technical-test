package technical.test.api.adapter.storage.models;

import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Value
@Document
public class AuthorDocument {
    @Id
    String id;
    String firstname;
    String lastname;
    Integer birthdate;
}
