package technical.test.api.product;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import technical.test.api.product.Product;

public interface ProductRepository extends ReactiveCrudRepository<Product, String> {

}