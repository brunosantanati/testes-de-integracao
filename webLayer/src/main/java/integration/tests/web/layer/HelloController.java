package integration.tests.web.layer;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping("/hello")
	public String getHello() {
		return "Hello";
	}

}
