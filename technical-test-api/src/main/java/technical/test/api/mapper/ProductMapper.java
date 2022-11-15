package technical.test.api.mapper;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.stereotype.Service;

import technical.test.api.model.Product;
import technical.test.api.model.dto.ProductDTO;

@Service
public class ProductMapper {

	public Product toEntity(ProductDTO productDto) {
		Product product = new Product();

		product.setReference(productDto.getReference());
		product.setName(productDto.getName());
		product.setPrice(productDto.getPrice());

		return product;
	}

	public ProductDTO toDto(Product product) {
		ProductDTO productDto = new ProductDTO();

		productDto.setReference(product.getReference());
		productDto.setName(product.getName());
		productDto.setPrice(product.getPrice());

		return productDto;
	}

	public Collection<Product> toEntity(Collection<ProductDTO> productDtos) {
		Collection<Product> products = new ArrayList<Product>();

		for (ProductDTO productDto : productDtos) {
			products.add(this.toEntity(productDto));
		}

		return products;
	}

	public Collection<ProductDTO> toDto(Collection<Product> products) {
		Collection<ProductDTO> productDtos = new ArrayList<ProductDTO>();

		for (Product product : products) {
			productDtos.add(this.toDto(product));
		}

		return productDtos;
	}
}
