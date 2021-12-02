package integration.tests.mockmvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

	@GetMapping
	public String getAll(Model model) {
		model.addAttribute("greeting","Hello");
		return "index";
	}

}
