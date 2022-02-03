package uk.gov.hmcts.reform.cwrdapi.service.impl;

import com.launchdarkly.sdk.server.LDClient;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FeatureToggleServiceImplTest {

    LDClient ldClient = mock(LDClient.class);
    FeatureToggleServiceImpl flaFeatureToggleService = mock(FeatureToggleServiceImpl.class);

    @Test
    public void testIsFlagEnabled() {
        flaFeatureToggleService = new FeatureToggleServiceImpl(ldClient, "rd");
        assertFalse(flaFeatureToggleService.isFlagEnabled("test", "test"));
    }

    @Test
    public void testIsFlagEnabled_true() {
        flaFeatureToggleService = new FeatureToggleServiceImpl(ldClient, "rd");
        when(flaFeatureToggleService.isFlagEnabled("test", "test")).thenReturn(true);
        assertTrue(flaFeatureToggleService.isFlagEnabled("test", "test"));
    }

    @Test
    public void mapServiceToFlagTest() {
        flaFeatureToggleService = new FeatureToggleServiceImpl(ldClient, "rd");
        flaFeatureToggleService.mapServiceToFlag();
        assertTrue(flaFeatureToggleService.getLaunchDarklyMap().size() >= 1);
    }
}
