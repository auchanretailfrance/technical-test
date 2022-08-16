package service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import technical.test.api.entity.Product;
import technical.test.api.repository.ProductRepository;
import technical.test.api.service.ProductService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TestProductService {

    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductService productService;

    @Test
    public void insertProduct_should_create_product(){
        when(productRepository.insert(any(Product.class))).thenReturn(new Product());

        productService.insertProduct(new Product());
        verify(productRepository, times(1)).insert(any(Product.class));
    }
}
