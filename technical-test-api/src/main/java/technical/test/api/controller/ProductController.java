package technical.test.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.api.entity.Product;
import technical.test.api.services.ProductService;

import java.util.Collection;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(maxAge = 3600)
public class ProductController {

    @Autowired
    private ProductService productService;


    @GetMapping
    public ResponseEntity<Flux<Product>> allProducts(){
        Flux<Product> products = productService.getAllProducts();
        if(products == null){
            return new ResponseEntity("302", HttpStatus.BAD_REQUEST);
        }else {
            return new ResponseEntity<Flux<Product>>(products, HttpStatus.OK);
        }
    }

   @PostMapping("createProduct")
    public ResponseEntity<Mono<Product>> createProduct (@RequestBody Product product){
        try{
            Mono<Product> result = productService.createProduct(product);
            return new ResponseEntity<>(result,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("insertProduct")
    public ResponseEntity<Mono<Product>> insertProduct (@RequestBody Product product){
        try{
            Mono<Product> value = productService.insertProduct(product);
            return new ResponseEntity<>(value,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("insertProducts")
    public ResponseEntity<Flux<Product>> insertProducts (@RequestBody Collection<Product> products){
        try{
            Flux<Product> value = productService.insertProducts(products);
            return new ResponseEntity<>(value,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
