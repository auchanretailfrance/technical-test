package technical.test.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("interview")
public class Interview {

    @Id
    private String id;

    public String interviewer;

    public String interviewee;

    public boolean approved = false;
}
