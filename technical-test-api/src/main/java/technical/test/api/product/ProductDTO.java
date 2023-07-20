package technical.test.api.product;

import java.math.BigDecimal;

public record ProductDTO(String sku, String name, String description, BigDecimal price, String brand, String category) {
}