package technical.test.api.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import technical.test.api.models.Product;

public interface ProductRepository extends ReactiveCrudRepository<Product, String> {

}