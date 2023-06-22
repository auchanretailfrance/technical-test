package technical.test.api.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@Document(collection = "Product")
@Data
public class Product {

    @Id
    private String _id;
    private String reference;
    private String name;
    private double price;

    public Product(String reference, String name, double price) {
        this.reference = reference;
        this.name = name;
        this.price = price;
    }

    public Product() {
    }
}
