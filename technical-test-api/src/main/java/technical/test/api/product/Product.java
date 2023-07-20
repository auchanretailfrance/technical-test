package technical.test.api.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Document(collection = "products")
@ToString
public class Product {
    @Id
    String sku;
    String name;
    String description;
    BigDecimal price;
    String brand;
    String category;
    LocalDateTime creationDate;

    public Product sku(String sku) {
        this.sku = sku;
        return this;
    }

    public Product name(String name) {
        this.name = name;
        return this;
    }

    public Product description(String description) {
        this.description = description;
        return this;
    }

    public Product price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public Product brand(String brand) {
        this.brand = brand;
        return this;
    }

    public Product category(String category) {
        this.category = category;
        return this;
    }

    static Product build(ProductDTO productDTO) {
        return Product.builder()
                .name(productDTO.name())
                .description(productDTO.description())
                .price(productDTO.price())
                .brand(productDTO.brand())
                .category(productDTO.category())
                .creationDate(LocalDateTime.now())
                .build();
    }

    Product modify(ProductDTO productDTO) {
        if (productDTO.name() != null) {
            this.name = productDTO.name();
        }
        if (productDTO.description() != null) {
            this.description = productDTO.description();
        }
        if (productDTO.brand() != null) {
            this.brand = productDTO.brand();
        }
        if (productDTO.price() != null) {
            this.price = productDTO.price();
        }
        if (productDTO.category() != null) {
            this.category = productDTO.category();
        }
        return this;
    }
}