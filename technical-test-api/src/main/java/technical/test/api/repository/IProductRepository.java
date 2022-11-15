package technical.test.api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import technical.test.api.model.Product;

@Repository("ProductRepository")
public interface IProductRepository extends MongoRepository<Product, String> {
}
