package uk.gov.hmcts.reform.cwrdapi.client.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uk.gov.hmcts.reform.cwrdapi.util.MappingField;

import java.util.List;
import javax.validation.constraints.NotEmpty;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CaseWorkerProfile extends CaseWorkerDomain {

    @MappingField(columnName = "FIRST NAME")
    @NotEmpty
    private String firstName;

    @MappingField(columnName = "LAST NAME")
    @NotEmpty
    private String lastName;

    @MappingField(columnName = "Official Email")
    @NotEmpty
    private String officialEmail;

    @MappingField(columnName = "Region Id")
    private int regionId;

    @MappingField(columnName = "Region")
    @NotEmpty
    private String regionName;

    @MappingField(clazz = Location.class, objectCount = 2)
    @NotEmpty(message = "no primary or secondary location exists")
    private List<Location> locations;

    @MappingField(columnName = "User type")
    @NotEmpty
    private String userType;

    @MappingField(clazz = Role.class, objectCount = 2)
    @NotEmpty(message = "no primary or secondary roles exists")
    private List<Role> roles;

    @MappingField(clazz = WorkArea.class, objectCount = 8)
    @NotEmpty(message = "no area of works exists")
    private List<WorkArea> workAreas;

    @MappingField(columnName = "IDAM Roles")
    private String idamRoles;

    @MappingField(columnName = "Suspended")
    private String suspended;
}