package integration.test.mvc.cadidate;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
public class CandidateController {

	private CandidateService candidateService;

	public CandidateController(CandidateService candidateService) {
		this.candidateService = candidateService;
	}

	@ModelAttribute
	public void addUserName(Model model, Principal principal) {
		model.addAttribute("userName", principal != null ? principal.getName() : "");
	}

	@GetMapping(value = "/")
	public String getAll(Model model) {
		model.addAttribute("candidates",candidateService.findAll());
		return "index";
	}

	@GetMapping("/add")
	@PreAuthorize("hasRole('ADMIN')")
	public String add(Model model) {
		model.addAttribute("candidate", new Candidate());
		return "add";
	}

	@PostMapping("/add")
	@PreAuthorize("hasRole('ADMIN')")
	public String save(@Valid Candidate candidate,
			BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "add";
		}

		candidateService.save(candidate);
		return "redirect:/";
	}

	@GetMapping("/update/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public String view(@PathVariable("id") Integer id,Model model){
		model.addAttribute("candidate",candidateService.findById(id));
		return "update";
	}

	@PutMapping("/update/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public String update(@PathVariable("id") Integer id, @Valid Candidate candidate, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			candidate.setId(id);
			return "update";
		}
		candidateService.save(candidate);
		return "redirect:/";
	}

	@GetMapping("/delete/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public String viewBeforeDelete(@PathVariable("id") Integer id, Model model){
		model.addAttribute("candidate",candidateService.findById(id));
		return "delete";
	}

	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public String delete(@PathVariable("id") Integer id, Model model) {
		candidateService.delete(id);
		return "redirect:/";
	}

}
