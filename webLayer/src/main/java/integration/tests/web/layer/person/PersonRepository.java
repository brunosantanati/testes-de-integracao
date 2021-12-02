package integration.tests.web.layer.person;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Integer> {

	Person findFirstByOrderByIdDesc();

}
