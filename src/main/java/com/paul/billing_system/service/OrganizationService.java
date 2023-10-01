package com.paul.billing_system.service;

import com.paul.billing_system.dto.OrganizationDTO;
import com.paul.billing_system.entity.Organization;
import com.paul.billing_system.enums.OrganizationTypes;
import com.paul.billing_system.repository.OrganizationRepository;
import com.paul.billing_system.repository.SpecialityRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrganizationService {
    private final OrganizationRepository organizationRepository;

    public OrganizationService(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    public Organization save(OrganizationDTO organizationDTO) {

        Organization organization = new Organization();
        organization.setName(organizationDTO.getName());
        organization.setAddress(organizationDTO.getAddress());
        organization.setContact(organizationDTO.getContact());
        OrganizationTypes organizationType = OrganizationTypes.getOrganizationTypeByLabel(organizationDTO.getType());
        organization.setType(organizationType);
        organization.setOrgCode(organizationDTO.getOrgCode());
        organization.setEmail(organizationDTO.getEmail());
        organization.setEmergencyContact(organizationDTO.getEmergencyContact());
        organization.setOperatingHour(organizationDTO.getOperatingHour());
        organization.setCreatedAt(new Date());

/*        List<Speciality> specialities = specialityRepository.findAll();

        organization.setSpecialities(specialities);*/
        organization =organizationRepository.save(organization);

        return organization;

    }


    public List<Organization> getAllOrganization(OrganizationTypes type, PageRequest pageRequest) {
        return organizationRepository.findByType(type, pageRequest);
    }

    public Organization getOrganizationById(Long id) {
        Optional<Organization> organization = organizationRepository.findById(id);
        return organization.orElse(null);
    }

    public Organization updateOrganizationProfile(OrganizationDTO organizationDTO, Long id) {
        Optional<Organization> organization = organizationRepository.findById(id);

        if (organization.isPresent()) {
            organization.get().setName(organizationDTO.getName());
            organization.get().setAddress(organizationDTO.getAddress());
            organization.get().setContact(organizationDTO.getContact());
            organization.get().setEmail(organizationDTO.getEmail());
            organization.get().setEmergencyContact(organizationDTO.getEmergencyContact());
            organization.get().setOperatingHour(organizationDTO.getOperatingHour());
            organization.get().setUpdatedAt(new Date());

            return organizationRepository.save(organization.get());
        }
        return null;
    }

    public List<Organization> searchOrgByName(String name, PageRequest pageRequest) {
        return organizationRepository.searchByName(name, pageRequest);
    }

    public Object getCreationTime(Long id) {
        return organizationRepository.findById(id).get().getCreatedAt();
    }
}