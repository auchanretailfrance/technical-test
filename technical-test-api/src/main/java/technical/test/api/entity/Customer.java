package technical.test.api.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document("customer")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    private String clientId;
    private String firstName;
    private String lastName;
    private String favoriteShop;
    private Date lastVisit;

}
