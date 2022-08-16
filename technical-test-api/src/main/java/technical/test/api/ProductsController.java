package technical.test.api;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import technical.test.api.entity.Product;
import technical.test.api.exception.ProductAlreadyExistsException;
import technical.test.api.service.ProductService;

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

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public String insertProduct() throws ProductAlreadyExistsException {
        Product p = new Product("REF1", "Couette 160/200", "Auchan", 20);
        if (!productService.findProductsByReference(p.getReference()).isEmpty()){
            throw new ProductAlreadyExistsException("Product with reference " + p.getReference() + " already exists");
        }
        productService.insertProduct(p);

        return "Product with reference " + p.getReference() + " created successfully";
    }
}
