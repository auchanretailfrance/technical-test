package technical.test.api.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import technical.test.api.entity.Product;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<Product, String> {
}
