package technical.test.api.services.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.api.entity.Product;
import technical.test.api.repository.ProductRepository;
import technical.test.api.services.ProductService;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Flux<Product> getAllProducts() {
        return productRepository.findAll();
    }

   @Override
    public Mono<Product> createProduct(Product product) {
        if(product == null || product.getName() == null || product.getName().isEmpty()) {
            throw new IllegalArgumentException("Product can't be null");
        }
       return productRepository.save(product).thenReturn(product);

    }

    public Mono<Product> insertProduct (Product product){
        try {
            return this.productRepository.insert(product);
        } catch (Exception e) {
            throw new IllegalArgumentException("Products were already inserted");
        }
    }

    @Override
    public Flux<Product> insertProducts(Collection<Product> products) {
        try {
            return this.productRepository.insert(products);
        } catch (Exception e) {
            throw new IllegalArgumentException("Products were already inserted");
        }
    }
}
