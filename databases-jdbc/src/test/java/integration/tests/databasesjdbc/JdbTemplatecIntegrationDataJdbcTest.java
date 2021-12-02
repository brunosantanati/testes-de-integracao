package integration.tests.databasesjdbc;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import integration.tests.databasesjdbc.product.Product;
import integration.tests.databasesjdbc.product.ProductDAO;
import integration.tests.databasesjdbc.product.ProductRowMapper;

@DataJdbcTest
class JdbTemplatecIntegrationDataJdbcTest {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private ProductDAO productDAO;

	@BeforeEach
	public void setup() {
		this.productDAO = new ProductDAO(jdbcTemplate);
	}

	@Test
	@Sql(statements = "insert into product (id, name,price) values(1,'Mouse',15)")
	public void productShouldBeRemovedWhenDeleteIsCalled() {
		productDAO.deleteById(1);

		Integer countAfterDelete = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM product", Integer.class);
		Assertions.assertThat(countAfterDelete).isEqualTo(0);
	}

	@Test
	public void productShouldBeInsertedWhenSaveIsCalled() {
		Product product = productDAO.save(new Product("Webcam",5));

		Product insertedProduct = jdbcTemplate.queryForObject("SELECT id, name, price FROM product WHERE id = ?",
				new ProductRowMapper(), product.getId());
		Assertions.assertThat(insertedProduct).isEqualTo(product);
	}

	@Test
	@Sql(statements = "insert into product (id, name,price) values(1,'Mouse',15)")
	public void findByIdReturnProduct() {
		Product product = productDAO.findById(1);

		Assertions.assertThat(product.getName()).isEqualTo("Mouse");
		Assertions.assertThat(product.getPrice()).isEqualTo(15);
	}


}
