package uk.gov.hmcts.reform.cwrdapi.controllers.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CaseWorkerProfileCreationResponse {

    private String caseWorkerRegistrationResponse;

    public CaseWorkerProfileCreationResponse(String caseWorkerRegistrationResponse) {

        this.caseWorkerRegistrationResponse = caseWorkerRegistrationResponse;
    }
}