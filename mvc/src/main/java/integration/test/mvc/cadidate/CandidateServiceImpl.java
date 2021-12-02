package integration.test.mvc.cadidate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CandidateServiceImpl implements CandidateService {

	@Autowired
	private CandidateRepository candidateRepository;

	@Override
	public Candidate save(Candidate candidate) {
		return candidateRepository.save(candidate);
	}

	@Override
	public List<Candidate> findAll() {
		List<Candidate> candidates = candidateRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
		if (candidates.isEmpty()) {
			return new ArrayList<>();
		}
		return candidates;
	}

	@Override
	public Candidate findById(Integer id) {
		Optional<Candidate> candidate = candidateRepository.findById(id);

		if(candidate.isEmpty()) {
			return new Candidate();
		}

		return candidate.get();
	}

	@Override
	public void delete(Integer id) {
		candidateRepository.deleteById(id);
	}

}
