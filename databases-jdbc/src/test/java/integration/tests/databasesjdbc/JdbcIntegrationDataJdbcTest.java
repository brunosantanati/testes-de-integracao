package integration.tests.databasesjdbc;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.test.context.jdbc.Sql;

import integration.tests.databasesjdbc.product.Product;
import integration.tests.databasesjdbc.product.ProductRepository;

@DataJdbcTest
class JdbcIntegrationDataJdbcTest {

	@Autowired
	private ProductRepository productRepository;

	@AfterEach
	public void cleanUp() {
		productRepository.deleteAll();
	}

	@Test
	@Sql(statements = "insert into product (id, name,price) values(1,'Mouse',15)")
	public void findByNameIgnoreCaseReturnProduct() {
		Product product = productRepository.findByNameIgnoreCase("mouse");
		Assertions.assertThat(product.getName()).isEqualTo("Mouse");
		Assertions.assertThat(product.getPrice()).isEqualTo(15);
	}

	@Test
	@Sql(statements = "insert into product (id,name,price) values(1,'Scanner',40)")
	@Sql(statements = "insert into product (id,name,price) values(2,'Mouse',15)")
	public void returnProductsSortByPriceAscendent() {
		List<Product> products = productRepository.findAllOrderedByPriceAsc();
		Assertions.assertThat(products).extracting(Product::getName).containsExactly("Mouse", "Scanner");
	}

}
