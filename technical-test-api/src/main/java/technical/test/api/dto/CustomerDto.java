package technical.test.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {
    private String clientId;
    private String firstName;
    private String lastName;
    private String favoriteShop;
    private Date lastVisit;
}
