package uk.gov.hmcts.reform.cwrdapi.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import uk.gov.hmcts.reform.cwrdapi.client.domain.CaseWorkerDomain;
import uk.gov.hmcts.reform.cwrdapi.client.domain.CaseWorkerProfile;
import uk.gov.hmcts.reform.cwrdapi.service.impl.JsrValidatorInitializer;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;
import static uk.gov.hmcts.reform.cwrdapi.TestSupport.buildCaseWorkerProfileData;

public class JsrValidatorInitializerTest {

    @Spy
    @InjectMocks
    JsrValidatorInitializer<CaseWorkerDomain> jsrValidatorInitializer;

    @Before
    public void init() {
        openMocks(this);
        jsrValidatorInitializer.initializeFactory();
    }

    @Test
    public void testGetNoInvalidJsrRecords() {
        List<CaseWorkerDomain> caseWorkerProfiles = buildCaseWorkerProfileData();
        List<CaseWorkerDomain> records = jsrValidatorInitializer.getInvalidJsrRecords(caseWorkerProfiles);
        assertEquals(0, records.size());
        verify(jsrValidatorInitializer).getInvalidJsrRecords(caseWorkerProfiles);
    }

    @Test
    public void testGetInvalidJsrRecords() {
        List<CaseWorkerDomain> caseWorkerProfiles = new ArrayList<>();
        CaseWorkerProfile profile = CaseWorkerProfile.builder().build();
        profile.setOfficialEmail("abc.com");
        caseWorkerProfiles.add(profile);
        List<CaseWorkerDomain> records = jsrValidatorInitializer.getInvalidJsrRecords(caseWorkerProfiles);
        assertEquals(1, records.size());
        verify(jsrValidatorInitializer).getInvalidJsrRecords(caseWorkerProfiles);
    }

    @Test
    public void testGetNoInvalidJsrRecords_whenEmailWithMixedCases() {
        List<CaseWorkerDomain> caseWorkerProfiles = buildCaseWorkerProfileData();
        CaseWorkerProfile record = (CaseWorkerProfile) caseWorkerProfiles.get(0);
        record.setOfficialEmail("tEst123-CRD3@JUSTICE.GOV.UK");
        List<CaseWorkerDomain> records = jsrValidatorInitializer.getInvalidJsrRecords(caseWorkerProfiles);
        assertEquals(0, records.size());
        verify(jsrValidatorInitializer).getInvalidJsrRecords(caseWorkerProfiles);
    }

    @Test
    public void testGetInvalidJsrRecords_whenEmailWithSpecialChars() {
        List<CaseWorkerDomain> caseWorkerProfiles = buildCaseWorkerProfileData();
        CaseWorkerProfile record = (CaseWorkerProfile) caseWorkerProfiles.get(0);
        record.setOfficialEmail("$%^&@justice.gov.uk");
        List<CaseWorkerDomain> records = jsrValidatorInitializer.getInvalidJsrRecords(caseWorkerProfiles);
        assertEquals(1, records.size());
        verify(jsrValidatorInitializer).getInvalidJsrRecords(caseWorkerProfiles);
    }

    @Test
    public void testGetInvalidJsrRecords_whenEmailWithSpaceInBetween() {
        List<CaseWorkerDomain> caseWorkerProfiles = buildCaseWorkerProfileData();
        CaseWorkerProfile record = (CaseWorkerProfile) caseWorkerProfiles.get(0);
        record.setOfficialEmail("user name@justice.gov.uk");
        List<CaseWorkerDomain> records = jsrValidatorInitializer.getInvalidJsrRecords(caseWorkerProfiles);
        assertEquals(1, records.size());
        verify(jsrValidatorInitializer).getInvalidJsrRecords(caseWorkerProfiles);
    }
}
