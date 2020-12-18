package uk.gov.hmcts.reform.cwrdapi.controllers.response;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CaseWorkerProfileCreationResponseTest {

    @Test
    public void testCaseWorkerProfileCreationResponse() {
        CaseWorkerProfileCreationResponse response = new CaseWorkerProfileCreationResponse();
        response.setCaseWorkerRegistrationResponse("response");

        assertThat(response).isNotNull();
        assertThat(response.getCaseWorkerRegistrationResponse()).isEqualTo("response");

        CaseWorkerProfileCreationResponse response1 =
                new CaseWorkerProfileCreationResponse("response");
        assertThat(response1).isNotNull();
        assertThat(response1.getCaseWorkerRegistrationResponse()).isEqualTo("response");

        CaseWorkerProfileCreationResponse response2 = new CaseWorkerProfileCreationResponse("response", "1");
        assertThat(response2.getCaseWorkerRegistrationResponse()).isEqualTo("response");
        assertThat(response2.getId()).isEqualTo("1");
    }
}


