package technical.test.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import technical.test.api.models.Product;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public record ProductDTO {
    private String name;
    private String description;
    private BigDecimal price;
    private String brand;
    private String category;

    public ProductDTO name(String name) {
        this.name = name;
        return this;
    }

    public ProductDTO description(String description) {
        this.description = name;
        return this;
    }

    public ProductDTO(Product product) {
        ProductDTO.builder()
                .name(product.getName())
                .description()
                .price()
                .brand()
                .category()
                .build();
    }
}
