package technical.test.api.services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import technical.test.api.exception.ProductAlreadyExistsException;
import technical.test.api.mapper.ProductMapper;
import technical.test.api.model.Product;
import technical.test.api.model.dto.ProductDTO;
import technical.test.api.repository.IProductRepository;

@Service
public class ProductServiceImpl implements IProductService {

	@Autowired
	private IProductRepository productRepository;

	@Autowired
	private ProductMapper productMapper;

	/*
	 * récupere tous les produits en intérogeant le repository et les transforment
	 * en DTO avant de les renvoyer au controller
	 */
	@Override
	public Collection<ProductDTO> findAll() {
		Collection<Product> products = this.productRepository.findAll();
		Collection<ProductDTO> productDtos = this.productMapper.toDto(products);

		return productDtos;
	}

	/*
	 * crée 3 produits prédefinis et les insert dans la collection des produit léve
	 * une exception si l'un des produit existe déjà
	 */
	@Override
	public void insertSomeProducts() throws ProductAlreadyExistsException {
		Collection<Product> products = new ArrayList<Product>();
		products.add(new Product("productReference-001", "productName1", 15.9));
		products.add(new Product("productReference-002", "productName2", 25.9));
		products.add(new Product("productReference-003", "productName3", 35.9));

		try {
			this.productRepository.insert(products);
		} catch (Exception e) {
			throw new ProductAlreadyExistsException("Products were already inserted");
		}
	}

	/*
	 * insert un nouveau produit recu en paramettre et le transforme en DTO avant de
	 * les renvoyer au controller léve une exception si le produit existe déjà
	 */
	@Override
	public ProductDTO create(ProductDTO productDto) throws ProductAlreadyExistsException {
		try {
			Product product = this.productMapper.toEntity(productDto);
			Product newProduct = this.productRepository.insert(product);
			ProductDTO newProductDto = this.productMapper.toDto(newProduct);

			return newProductDto;
		} catch (Exception e) {
			throw new ProductAlreadyExistsException(
					"Product with reference " + productDto.getReference() + " already exists");
		}
	}
}
