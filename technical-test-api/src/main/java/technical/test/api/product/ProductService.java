package technical.test.api.services;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.api.dtos.ProductDTO;
import technical.test.api.mappers.ProductMapper;
import technical.test.api.models.Product;
import technical.test.api.repositories.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public Mono<ProductDTO> createProduct(ProductDTO productDTO) {
        Product product = productMapper.toProduct(productDTO);
        return productRepository.save(product).map(ProductDTO::new);
    }

    public Flux<Product> getAllProducts() {
        return productRepository.findAll();
    }

    ProductDTO toProductDto(){

    }
}
