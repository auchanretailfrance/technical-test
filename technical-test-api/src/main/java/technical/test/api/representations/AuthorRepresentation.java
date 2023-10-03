package technical.test.api.representations;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthorRepresentation {
    private String id;
    private Integer birthdate;
    private String firstname;
    private String lastname;
}
