package technical.test.api.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin(origins= "*")
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
        return productService.createProduct(productDTO)
                .onErrorResume(IllegalArgumentException.class, ex -> Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage())));
    }

    @GetMapping
    public Flux<ProductDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{sku}")
    public Mono<ProductDTO> getProduct(@PathVariable String sku) {
        return productService.getProduct(sku);
    }

    @PatchMapping("/{sku}")
    public Mono<ProductDTO> modifyProduct(@PathVariable String sku, @RequestBody ProductDTO productDTO) {
        return productService.modifyProduct(sku, productDTO);
    }

    @PutMapping("/{sku}")
    public Mono<ProductDTO> updateProduct(@PathVariable String sku, @RequestBody ProductDTO productDTO) {
        return productService.updateProduct(sku, productDTO);
    }

    @DeleteMapping("/{sku}")
    public Mono<Boolean> deleteProduct(@PathVariable String sku) {
        return productService.deleteProduct(sku);
    }
}