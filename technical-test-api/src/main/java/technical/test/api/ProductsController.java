package technical.test.api;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import technical.test.api.dto.ProductDTO;
import technical.test.api.entity.Product;
import technical.test.api.exception.ProductAlreadyExistsException;
import technical.test.api.exception.ProductNotFoundException;
import technical.test.api.service.ProductService;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;

@RestController
@RequestMapping("/api/v1/products")
@AllArgsConstructor
public class ProductsController {

    private ProductService productService;

    @ExceptionHandler(ProductAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String onProductAlreadyExistsError(Exception e){
        return e.getMessage();
    }

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String onProductNotFoundError(Exception e){
        return e.getMessage();
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public String CreateProduct() throws ProductAlreadyExistsException {
        Product p = new Product("REF1", "Couette 160/200", "Auchan", 20);
        productService.insertProduct(p);
        return "Product with reference " + p.getReference() + " created successfully";
    }

    @PostMapping(value = "", consumes = APPLICATION_JSON_VALUE, produces = TEXT_PLAIN_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public String insertProduct(@RequestBody ProductDTO productDTO) throws ProductAlreadyExistsException {
        Product p = new Product(
                productDTO.getReference(),
                productDTO.getName(),
                productDTO.getBrand(),
                productDTO.getPrice()
        );

        productService.insertProduct(p);
        return "Product with reference " + productDTO.getReference() + " created successfully";
    }

    @CrossOrigin
    @GetMapping(value = "", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDTO> getAllProducts() throws ProductNotFoundException {
        List<Product> products = productService.findAllProducts();
        if (products.isEmpty()){
            throw new ProductNotFoundException("No product found");
        }
        List<ProductDTO> test = products
                .stream()
                .map(p -> new ProductDTO(
                        p.getReference(),
                        p.getName(),
                        p.getBrand(),
                        p.getPrice()
                ))
                .collect(Collectors.toList());
        return test;
    }
}
