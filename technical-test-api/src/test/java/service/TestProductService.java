package service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import technical.test.api.entity.Product;
import technical.test.api.exception.ProductAlreadyExistsException;
import technical.test.api.repository.ProductRepository;
import technical.test.api.service.ProductService;

import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TestProductService {

    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductService productService;

    @Test
    public void insertProduct_should_create_product() throws Exception {
        when(productRepository.insert(any(Product.class))).thenReturn(new Product());

        productService.insertProduct(new Product());
        verify(productRepository, times(1)).insert(any(Product.class));
    }

    @Test(expected = ProductAlreadyExistsException.class)
    public void insertProduct_should_throw_ProductAlreadyExistsException() throws Exception {
        when(productRepository.findProductsByReference(anyString())).thenReturn(Arrays.asList(new Product(), new Product()));

        productService.insertProduct(new Product("", "", "", 1));
    }

    @Test
    public void fetchAllProducts_should_return_all_products(){
        when(productRepository.findAll()).thenReturn(Arrays.asList(new Product(), new Product(), new Product()));

        assertThat(productService.findAllProducts()).asList().hasSize(3);
    }

    @Test
    public void fetchAllProducts_should_return_empty_list(){
        when(productRepository.findAll()).thenReturn(new ArrayList<>());

        assertThat(productService.findAllProducts()).asList().hasSize(0);
    }
}
