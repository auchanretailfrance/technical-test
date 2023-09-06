package technical.test.api.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class AuthorDTO {
    private String id;
    private String firstName;
    private String lastName;
    private int birthdate;
}
