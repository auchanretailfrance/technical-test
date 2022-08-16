package technical.test.api.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import technical.test.api.entity.Product;
import technical.test.api.repository.ProductRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {


    private final ProductRepository productRepository;

    public void insertProduct(Product product){
        productRepository.insert(product);
    }

    public List<Product> findProductsByReference(String reference){
        return productRepository.findProductsByReference(reference);
    }
}
