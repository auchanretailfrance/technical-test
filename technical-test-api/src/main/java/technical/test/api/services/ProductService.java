package technical.test.api.services;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.api.entity.Product;

import java.util.Collection;

public interface ProductService {

    Flux<Product> getAllProducts();

    Mono<Product> createProduct (Product product);

    Mono<Product> insertProduct (Product product);

    Flux<Product> insertProducts (Collection<Product> products);
}
