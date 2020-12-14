package uk.gov.hmcts.reform.cwrdapi.service;

import uk.gov.hmcts.reform.cwrdapi.client.domain.ServiceRoleMapping;
import uk.gov.hmcts.reform.cwrdapi.controllers.request.CaseWorkersProfileCreationRequest;
import uk.gov.hmcts.reform.cwrdapi.controllers.response.IdamRolesMappingResponse;
import uk.gov.hmcts.reform.cwrdapi.domain.CaseWorkerProfile;

import java.util.List;

public interface CaseWorkerService {

    List<CaseWorkerProfile> processCaseWorkerProfiles(List<CaseWorkersProfileCreationRequest>
                                                                caseWorkersProfileCreationRequest);

    /**
     * Builds the idam role mappings for case worker roles.
     * @param serviceRoleMappings list of ServiceRoleMapping
     * @return IdamRoleAssocResponse
     */
    IdamRolesMappingResponse buildIdamRoleMappings(List<ServiceRoleMapping> serviceRoleMappings);

    void publishCaseWorkerDataToTopic(List<CaseWorkerProfile> caseWorkerData);
}

