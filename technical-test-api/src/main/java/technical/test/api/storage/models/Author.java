package technical.test.api.storage.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "Authors")
public class Author {

    @Id
    private String id;
    private Integer birthdate;
    private String firstname;
    private String lastname;

}
