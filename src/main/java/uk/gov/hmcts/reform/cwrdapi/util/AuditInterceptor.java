package uk.gov.hmcts.reform.cwrdapi.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.HandlerInterceptor;
import uk.gov.hmcts.reform.cwrdapi.domain.CaseWorkerAudit;
import uk.gov.hmcts.reform.cwrdapi.oidc.JwtGrantedAuthoritiesConverter;
import uk.gov.hmcts.reform.cwrdapi.repository.AuditRepository;
import uk.gov.hmcts.reform.cwrdapi.service.IValidationService;

import java.util.List;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static uk.gov.hmcts.reform.cwrdapi.util.AuditStatus.IN_PROGRESS;
import static uk.gov.hmcts.reform.cwrdapi.util.CaseWorkerConstants.FILE;

@MultipartConfig
@Component
public class AuditInterceptor implements HandlerInterceptor {

    @Autowired
    IValidationService validationServiceFacadeImpl;

    @Autowired
    AuditRepository auditRepository;

    @Autowired
    @Lazy
    private JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             @NotNull HttpServletResponse response, @NotNull Object handler) {

        List<CaseWorkerAudit> audits = auditRepository.findByAuthenticatedUserId(
            jwtGrantedAuthoritiesConverter.getUserInfo().getName());

        if (CollectionUtils.isEmpty(audits) || !AuditStatus.IN_PROGRESS.getStatus().equals(audits.get(0).getStatus())) {
            String fileName = EMPTY;
            MultipartFile multipartFile = ((MultipartHttpServletRequest) request).getFile(FILE);
            if (nonNull(multipartFile)) {
                fileName = multipartFile.getOriginalFilename();
            }
            //Starts CWR Auditing with Job Status in Progress.
            validationServiceFacadeImpl.startCaseworkerAuditing(IN_PROGRESS, fileName);
        }
        return true;
    }
}
