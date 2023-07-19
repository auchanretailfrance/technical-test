package technical.test.api.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import technical.test.api.dtos.ProductDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "products")
public class Product {
    @Id
    private String sku; // Using the SKU as the unique identifier
    private String name;
    private String description;
    private BigDecimal price;
    private String brand;
    private String category;
    private LocalDateTime creationDate;

    Product(ProductDTO record){

    }


}