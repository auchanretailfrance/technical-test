import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import technical.test.api.ProductsController;
import technical.test.api.entity.Product;
import technical.test.api.service.ProductService;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ProductsController.class})
@AutoConfigureMockMvc
public class TestProductsController {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    public void createProduct_should_return_201() throws Exception {
        when(productService.findProductsByReference(anyString())).thenReturn(new ArrayList<>());
        doNothing().when(productService).insertProduct(any(Product.class));

        mockMvc.perform(post("/api/v1/products/create"))
                .andExpect(status().isCreated());
    }
}
