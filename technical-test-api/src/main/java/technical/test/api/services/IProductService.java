package technical.test.api.services;

import java.util.Collection;

import technical.test.api.model.dto.ProductDTO;

public interface IProductService {

	Collection<ProductDTO> findAll();

	void insertSomeProducts();

	ProductDTO create(ProductDTO productDto);

}
