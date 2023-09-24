package com.paul.billing_system.controller;

import com.paul.billing_system.dto.DoctorDTO;
import com.paul.billing_system.dto.OrganizationDTO;
import com.paul.billing_system.dto.PatientsDTO;
import com.paul.billing_system.entity.Doctors;
import com.paul.billing_system.entity.Organization;
import com.paul.billing_system.entity.Patients;
import com.paul.billing_system.entity.Specialist;
import com.paul.billing_system.enums.OrganizationTypes;
import com.paul.billing_system.service.OrganizationServices;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.paul.billing_system.controller.AuthController.getErrorDetails;

@RestController
@RequestMapping("/organization")
@PreAuthorize("hasAuthority('ROLE_ROOT')")
public class OrganizationController {
    private final OrganizationServices organizationServices;

    public OrganizationController(OrganizationServices organizationServices) {
        this.organizationServices = organizationServices;
    }

    @PostMapping("/create")
    public ResponseEntity<?> save(@Valid @RequestBody OrganizationDTO organizationDTO, BindingResult bindingResult) {
        ResponseEntity<?> errorDetails = getErrorDetails(bindingResult);
        if (errorDetails != null) return errorDetails;
        OrganizationDTO organizationDTO1 = OrganizationDTO.form(organizationServices.save(organizationDTO));
        return new ResponseEntity<>(organizationDTO1, HttpStatus.CREATED);
    }


    @GetMapping("/OrganizationType")
    public List<String> getAllOrganizationTypesList() {
        return OrganizationTypes.getAllOrganizationTypesList();
    }

    @GetMapping("/{type}")
    public ResponseEntity<?> getAllOrganization(@PathVariable String type, @RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "10") int size) {
        List<Organization> organizations = organizationServices.getAllOrganization(OrganizationTypes.getOrganizationTypeByLabel(type), PageRequest.of(page, size));
        List<OrganizationDTO> organizationDTOList = organizations.stream()
                .map(OrganizationDTO::form)
                .collect(Collectors.toList());
/*        List<String> organizationName = organizations.stream()
                .map(Organization::getName).toList();*/
        return new ResponseEntity<>(organizationDTOList, HttpStatus.OK);
    }

    @PutMapping("/updateOrganizationProfile/{id}")
    public ResponseEntity<?> updateOrganizationProfile(@Valid @PathVariable Long id, @RequestBody OrganizationDTO organizationDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>("Validation errors: " + bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        OrganizationDTO organizationDTO1 = OrganizationDTO.form(organizationServices.updateOrganizationProfile(organizationDTO, id));
        return new ResponseEntity<>(organizationDTO1, HttpStatus.OK);
    }

    @GetMapping("/getOrganizationById/{id}")
    public ResponseEntity<?> getOrganizationById(@PathVariable Long id) {
        OrganizationDTO organizationDTO = OrganizationDTO.form(organizationServices.getOrganizationByid(id));
        return new ResponseEntity<>(organizationDTO, HttpStatus.OK);
    }
}



        /*    @PostMapping("/createDoctor/{orgId}/{specialityName}")
    public ResponseEntity<?> createDoctor(@PathVariable Long orgId, @PathVariable String specialityName) {
        Map<Specialist, List<Doctors>> specialistDoctorsMap = organizationServices.getSpecialistDoctorsMap(orgId,specialityName);
        return new ResponseEntity<>(specialistDoctorsMap, HttpStatus.OK);
    }*/