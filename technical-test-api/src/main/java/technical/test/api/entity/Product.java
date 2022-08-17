package technical.test.api.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    private String id;
    @Indexed(unique = true)
    private String reference;
    private String name;
    private String brand;
    private Integer price;

    public Product(String reference, String name, String brand, Integer price) {
        this.reference = reference;
        this.name = name;
        this.brand = brand;
        this.price = price;
    }
}
