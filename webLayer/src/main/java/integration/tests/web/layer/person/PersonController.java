package integration.tests.web.layer.person;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

	private PersonRepository personRepository;

	public PersonController(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}

	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping
	public Person getPerson() {
		return personRepository.findFirstByOrderByIdDesc();
	}

	@ResponseStatus(value = HttpStatus.CREATED)
	@PostMapping
	public Person postHello(@RequestBody Person person) {
		return personRepository.save(person);
	}

	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@PutMapping
	public void putHello(@RequestBody Person person) {
		personRepository.save(person);
	}

	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Integer id ) {
		personRepository.deleteById(id);
	}

}
