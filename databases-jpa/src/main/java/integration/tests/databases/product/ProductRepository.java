package integration.tests.databases.product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Integer>
{

	Product findByNameIgnoreCase(String name);

	@Query("select p from Product p order by p.price asc")
	List<Product> findAllOrderedByPriceAsc();

}
