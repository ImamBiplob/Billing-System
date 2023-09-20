package com.paul.billing_system.dto;

import com.paul.billing_system.entity.Organization;
import com.paul.billing_system.entity.Specialist;
import com.paul.billing_system.enums.OrganizationTypes;
import com.paul.billing_system.enums.UserRoles;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationDTO {

    private Long id;

    @NotEmpty(message = "Name should not be empty")
    private String name;

    @NotEmpty(message = "Address Should Not Be Empty")
    private String address;

    private String contact;

    private String type;

    @Email(message = "Email should be valid")
    @Column(unique = true)
    @NotEmpty(message = "Email should not be empty")
    private String email;

    private String emergencyContact;

    private String operatingHour;

    private List<UserInfoDTO> orgAdmin;

    private List<Specialist> specialist;

    public static OrganizationDTO form(Organization organization) {
        OrganizationDTO organizationDTO = new OrganizationDTO();
        organizationDTO.setId(organization.getId());
        organizationDTO.setName(organization.getName());
        organizationDTO.setAddress(organization.getAddress());
        organizationDTO.setContact(organization.getContact());
        String organizationTypes = OrganizationTypes.getLabelByOrganizationType(organization.getType());
        organizationDTO.setType(organizationTypes);
        organizationDTO.setEmail(organization.getEmail());
        organizationDTO.setEmergencyContact(organization.getEmergencyContact());
        organizationDTO.setOperatingHour(organization.getOperatingHour());
        organizationDTO.setOrgAdmin(organization.getOrgAdmin()!=null ? organization.getOrgAdmin().stream()
                .filter(adminDTO -> UserRoles.getUserRolesByLabel("ROLE_ORG_ADMIN").equals(adminDTO.getRoles()))
                .map(UserInfoDTO::form).toList() : null);
        organizationDTO.setSpecialist(organization.getSpecialists() != null ? organization.getSpecialists() : null);
        return organizationDTO;
    }
}
