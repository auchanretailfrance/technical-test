package technical.test.api.product;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.api.sku.SKUGenerator;

@Service
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Mono<ProductDTO> createProduct(ProductDTO productDTO) {
        log.info("Call createProduct with params : productDTO={}", productDTO);
        if (productDTO.sku() == null) {
            return save(productDTO);
        }
        return productRepository.findById(productDTO.sku())
                .flatMap(product -> save(product, productDTO));
    }

    @Transactional
    public Mono<ProductDTO> modifyProduct(String sku, ProductDTO productDTO) {
        log.info("Call modifyProduct with params : sku={}, productDTO={}", sku, productDTO);
        return productRepository.findById(sku)
                .map(product -> product.modify(productDTO))
                .map(this::toProductDto);
    }

    @Transactional
    public Mono<ProductDTO> updateProduct(String sku, ProductDTO productDTO) {
        log.info("Call updateProduct with params : sku={}, productDTO={}", sku, productDTO);
        return productRepository.findById(sku)
                .map(product -> product.name(productDTO.name())
                        .brand(productDTO.brand())
                        .description(productDTO.description())
                        .price(productDTO.price())
                        .category(productDTO.category()))
                .map(this::toProductDto);
    }

    public Mono<ProductDTO> getProduct(String sku) {
        log.info("Call getProduct with params : sku={}", sku);
        if (sku == null) {
            log.error("SKU is null");
            throw new IllegalArgumentException("SKU is null");
        }
        return productRepository.findById(sku)
                .map(this::toProductDto);
    }

    public Flux<ProductDTO> getAllProducts() {
        log.info("Call getAllProducts");
        return productRepository.findAll()
                .map(this::toProductDto);
    }

    public Mono<Boolean> deleteProduct(String sku) {
        return productRepository.deleteById(sku).map(unused -> true);
    }

    private ProductDTO toProductDto(Product product) {
        return new ProductDTO(product.sku, product.name, product.description, product.price, product.brand, product.category);
    }

    private String getSku(ProductDTO productDTO) {
        if (productDTO.sku() == null) {
            return SKUGenerator.generateSku();
        }
        return productDTO.sku();
    }

    private Mono<ProductDTO> save(Product product, ProductDTO productDTO) {
        if (product.sku != null) {
            log.error("SKU already exist : {}", product.sku);
            throw new IllegalArgumentException("SKU already exist");
        }
        return save(productDTO);
    }

    private Mono<ProductDTO> save(ProductDTO productDTO) {
        return productRepository.save(Product.build(productDTO).sku(getSku(productDTO)))
                .map(this::toProductDto);
    }
}