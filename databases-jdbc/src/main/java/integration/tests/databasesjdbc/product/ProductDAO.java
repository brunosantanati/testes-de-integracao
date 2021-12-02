package integration.tests.databasesjdbc.product;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
@Repository
public class ProductDAO {

	private JdbcTemplate jdbcTemplate;

	public ProductDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public Product findById(Integer id) {
		return jdbcTemplate.queryForObject("SELECT id, name, price FROM product WHERE id = ?", new ProductRowMapper(), id);
	}

	public void deleteById(Integer id) {
		jdbcTemplate.update("DELETE FROM product where id = ?", id);
	}

	public Product save(Product product) {
		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
				.withTableName("product")
				.usingGeneratedKeyColumns("id");
		Integer id = simpleJdbcInsert.executeAndReturnKey(product.toMap()).intValue();
		product.setId(id);
		return product;
	}

}
