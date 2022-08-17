import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import technical.test.api.ProductsController;
import technical.test.api.dto.ProductDTO;
import technical.test.api.entity.Product;
import technical.test.api.exception.ProductAlreadyExistsException;
import technical.test.api.service.ProductService;

import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ProductsController.class})
@EnableWebMvc
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

    @Test
    public void insertProduct_should_return_201() throws Exception {
        when(productService.findProductsByReference(anyString())).thenReturn(new ArrayList<>());
        doNothing().when(productService).insertProduct(any(Product.class));

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(new ProductDTO("a", "b", "c", 10));

        mockMvc.perform(post("/api/v1/products")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    public void insertProduct_should_throw_ProductAlreadyExistsException() throws Exception {
        doThrow(ProductAlreadyExistsException.class).when(productService).insertProduct(any(Product.class));

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(new ProductDTO("a", "b", "c", 10));

        mockMvc.perform(post("/api/v1/products")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(json))
                .andExpect(status().isConflict());
    }

    @Test
    public void getAllProducts_should_return_list_of_products_and_status_200() throws Exception {
        when(productService.findAllProducts()).thenReturn(Arrays.asList(
                new Product("p1", "n1", "b1", 1),
                new Product("p2", "n2", "b2", 1)
        ));

        mockMvc.perform(get("/api/v1/products").contentType(APPLICATION_JSON_VALUE)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(2)));
    }

    @Test
    public void getAllProducts_should_return_204_when_ProductNotFoundException() throws Exception {
        when(productService.findAllProducts()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/v1/products").contentType(APPLICATION_JSON_VALUE)).andDo(print())
                .andExpect(status().isNoContent());
    }
}
