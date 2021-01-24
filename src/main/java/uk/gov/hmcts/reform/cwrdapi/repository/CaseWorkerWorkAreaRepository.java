package uk.gov.hmcts.reform.cwrdapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.hmcts.reform.cwrdapi.domain.CaseWorkerProfile;
import uk.gov.hmcts.reform.cwrdapi.domain.CaseWorkerWorkArea;

import java.util.List;

@Repository
@Transactional
public interface CaseWorkerWorkAreaRepository extends JpaRepository<CaseWorkerWorkArea, Long> {
    void deleteByCaseWorkerProfileIn(List<CaseWorkerProfile> caseWorkerProfileList);
}
