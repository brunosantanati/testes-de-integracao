package integration.tests.databasesjdbc.product;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {

	Product findByNameIgnoreCase(String name);

	@Query("select * from product p order by p.price asc")
	List<Product> findAllOrderedByPriceAsc();

}
