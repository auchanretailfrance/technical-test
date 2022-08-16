package repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.junit4.SpringRunner;
import technical.test.api.entity.Product;
import technical.test.api.repository.ProductRepository;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {ProductRepository.class})
@EnableMongoRepositories(basePackages="technical.test.api.repository")
@EnableAutoConfiguration
@RunWith(SpringRunner.class)
public class TestProductRepository {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void insertProduct(){
        Product p = new Product(
                "TestRef",
                "TestName",
                "testBrand",
                10
        );

        productRepository.insert(p);
        assertThat(productRepository.findProductsByReference("TestRef")).hasSize(1);
    }
}
