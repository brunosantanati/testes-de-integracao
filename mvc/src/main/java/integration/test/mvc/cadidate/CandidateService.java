package integration.test.mvc.cadidate;

import java.util.List;

public interface CandidateService {

	Candidate save(Candidate candidate);

	List<Candidate> findAll();

	Candidate findById(Integer id);

	void delete(Integer id);

}
