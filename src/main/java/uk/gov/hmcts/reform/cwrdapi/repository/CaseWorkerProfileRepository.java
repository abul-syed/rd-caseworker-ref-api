package uk.gov.hmcts.reform.cwrdapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uk.gov.hmcts.reform.cwrdapi.domain.CaseWorkerProfile;

import javax.mail.search.SearchTerm;
import java.util.List;
import java.util.Set;

@Repository
public interface CaseWorkerProfileRepository extends JpaRepository<CaseWorkerProfile, Long> {

    List<CaseWorkerProfile> findByEmailIdIn(Set<String> emailIds);

    List<CaseWorkerProfile> findByCaseWorkerIdIn(List<String> caseWorkerId);
}
