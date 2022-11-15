package technical.test.api.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import technical.test.api.exception.ProductAlreadyExistsException;
import technical.test.api.model.Response;
import technical.test.api.model.dto.ProductDTO;
import technical.test.api.services.IProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

	@Autowired
	private IProductService productService;

	// se déclenche lors de l'ajout d'un produit déjà existant
	@ExceptionHandler(ProductAlreadyExistsException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public String onProductAlreadyExistsError(Exception e) {
		return e.getMessage();
	}

	// renvoie la liste de tous les produits
	@GetMapping("/")
	@CrossOrigin(origins = "http://localhost:8081")
	public Response<Collection<ProductDTO>> findAllProducts() {
		Collection<ProductDTO> products = this.productService.findAll();

		return new Response<>(products, HttpStatus.OK);
	}

	// insert 3 produits par defaut
	@PutMapping("/insert")
	@ResponseStatus(HttpStatus.CREATED)
	public void insertSomeProducts() {
		this.productService.insertSomeProducts();
	}

	// ajoute un nouveau produit
	@PostMapping("/")
	public Response<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
		ProductDTO newProductDto = this.productService.create(productDTO);

		return new Response<>(newProductDto, HttpStatus.CREATED);
	}
}
