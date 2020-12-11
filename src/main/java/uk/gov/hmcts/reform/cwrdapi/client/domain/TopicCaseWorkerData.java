package uk.gov.hmcts.reform.cwrdapi.client.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Setter;

import java.util.List;

@Setter
public class TopicCaseWorkerData {
    @JsonProperty
    private List<String> userIds;
}
