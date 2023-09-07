package technical.test.api.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateAuthorDTO {
    private String firstName;
    private String lastName;
    private int birthdate;
}
