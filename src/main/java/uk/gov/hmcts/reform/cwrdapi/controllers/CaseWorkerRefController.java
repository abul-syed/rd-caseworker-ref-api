package uk.gov.hmcts.reform.cwrdapi.controllers;

import com.nimbusds.oauth2.sdk.util.CollectionUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.hmcts.reform.cwrdapi.client.domain.ServiceRoleMapping;
import uk.gov.hmcts.reform.cwrdapi.controllers.advice.InvalidRequestException;
import uk.gov.hmcts.reform.cwrdapi.controllers.request.CaseWorkersProfileCreationRequest;
import uk.gov.hmcts.reform.cwrdapi.controllers.response.CaseWorkerProfileCreationResponse;
import uk.gov.hmcts.reform.cwrdapi.controllers.response.IdamRolesMappingResponse;
import uk.gov.hmcts.reform.cwrdapi.domain.CaseWorkerProfile;
import uk.gov.hmcts.reform.cwrdapi.service.CaseWorkerService;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static uk.gov.hmcts.reform.cwrdapi.util.CaseWorkerConstants.BAD_REQUEST;
import static uk.gov.hmcts.reform.cwrdapi.util.CaseWorkerConstants.FORBIDDEN_ERROR;
import static uk.gov.hmcts.reform.cwrdapi.util.CaseWorkerConstants.INTERNAL_SERVER_ERROR;
import static uk.gov.hmcts.reform.cwrdapi.util.CaseWorkerConstants.NO_DATA_FOUND;
import static uk.gov.hmcts.reform.cwrdapi.util.CaseWorkerConstants.UNAUTHORIZED_ERROR;

@RequestMapping(
        path = "/refdata/case-worker"
)
@RestController
@Slf4j
public class CaseWorkerRefController {

    @Autowired
    CaseWorkerService caseWorkerService;

    @ApiOperation(
            value = "This API creates caseworker profiles",
            authorizations = {
                    @Authorization(value = "ServiceAuthorization"),
                    @Authorization(value = "Authorization")
            }
    )
    @ApiResponses({
            @ApiResponse(
                    code = 201,
                    message = "Successfully created caseworker user profiles",
                    response = String.class,
                    responseContainer = "list"
            ),
            @ApiResponse(
                    code = 400,
                    message = BAD_REQUEST
            ),
            @ApiResponse(
                    code = 401,
                    message = UNAUTHORIZED_ERROR
            ),
            @ApiResponse(
                    code = 403,
                    message = FORBIDDEN_ERROR
            ),
            @ApiResponse(
                    code = 500,
                    message = INTERNAL_SERVER_ERROR
            )
    })
    @PostMapping(
            path = "/users/",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    @Secured("cwd-admin")
    public ResponseEntity<Object> createCaseWorkerProfiles(@RequestBody List<CaseWorkersProfileCreationRequest>
                                                                   caseWorkersProfileCreationRequest) {
        if (CollectionUtils.isEmpty(caseWorkersProfileCreationRequest)) {

            throw new InvalidRequestException("Caseworker Profiles Request is empty");
        }
        caseWorkerService.processCaseWorkerProfiles(caseWorkersProfileCreationRequest);
        return ResponseEntity
                .status(201)
                .body(new CaseWorkerProfileCreationResponse("Case Worker Profiles Created."));

    }

    @ApiOperation(
            value = "This API builds the idam role mappings for case worker roles",
            authorizations = {
                    @Authorization(value = "ServiceAuthorization"),
                    @Authorization(value = "Authorization")
            }
    )
    @ApiResponses({
            @ApiResponse(
                    code = 201,
                    message = "Successfully built idam role mappings for case worker roles",
                    response = IdamRolesMappingResponse.class
            ),
            @ApiResponse(
                    code = 400,
                    message = BAD_REQUEST
            ),
            @ApiResponse(
                    code = 401,
                    message = UNAUTHORIZED_ERROR
            ),
            @ApiResponse(
                    code = 403,
                    message = FORBIDDEN_ERROR
            ),
            @ApiResponse(
                    code = 500,
                    message = INTERNAL_SERVER_ERROR
            )
    })
    @PostMapping(
            path = "/idam-roles-mapping/",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    //Sonar reported the below code as Security Hotspot with LOW priority.
    //Currently it has been made false positive and reviewed as safe.
    @Secured("cwd-admin")
    public ResponseEntity<Object> buildIdamRoleMappings(@RequestBody List<ServiceRoleMapping>
                                                                   serviceRoleMappings) {
        if (CollectionUtils.isEmpty(serviceRoleMappings)) {
            throw new InvalidRequestException("ServiceRoleMapping Request is empty");
        }
        IdamRolesMappingResponse idamRolesMappingResponse =
                caseWorkerService.buildIdamRoleMappings(serviceRoleMappings);
        return ResponseEntity
                .status(idamRolesMappingResponse.getStatusCode())
                .body(idamRolesMappingResponse);
    }

    @ApiOperation(
            value = "This API gets the User details from Caseworker Profile",
            authorizations = {
                    @Authorization(value = "ServiceAuthorization"),
                    @Authorization(value = "Authorization")
            }
    )
    @ApiResponses({
            @ApiResponse(
                    code = 200,
                    message = "Successfully fetched the Caseworker profile(s)",
                    response = CaseWorkerProfile.class
            ),
            @ApiResponse(
                    code = 400,
                    message = BAD_REQUEST
            ),
            @ApiResponse(
                    code = 401,
                    message = UNAUTHORIZED_ERROR
            ),
            @ApiResponse(
                    code = 403,
                    message = FORBIDDEN_ERROR
            ),
            @ApiResponse(
                    code = 404,
                    message = NO_DATA_FOUND
            ),
            @ApiResponse(
                    code = 500,
                    message = INTERNAL_SERVER_ERROR
            )
    })
    @PostMapping(
            path = "/users/fetchUsersById/",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    @Secured("cwd-admin")
    public ResponseEntity<Object> fetchCaseworkersById(@RequestBody List<String> caseWorkerIds) {
        long currentTimeMillis = System.currentTimeMillis();
        if (CollectionUtils.isEmpty(caseWorkerIds)) {
            throw new InvalidRequestException("Caseworker request is empty");
        }
        ResponseEntity<Object> response = caseWorkerService.fetchCaseworkersById(caseWorkerIds);
        log.info("Time taken by fetchCaseworkersById method in Controller = "
                + (System.currentTimeMillis() - currentTimeMillis));
        return response;
    }
}
