package technical.test.api.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import technical.test.api.entity.Product;
import technical.test.api.exception.ProductAlreadyExistsException;
import technical.test.api.repository.ProductRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {


    private final ProductRepository productRepository;

    public void insertProduct(Product product) throws ProductAlreadyExistsException {
        if (!findProductsByReference(product.getReference()).isEmpty()){
            throw new ProductAlreadyExistsException("Product with reference " + product.getReference() + " already exists");
        }
        productRepository.insert(product);
    }

    public List<Product> findProductsByReference(String reference){
        return productRepository.findProductsByReference(reference);
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }
}
