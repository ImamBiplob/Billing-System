package com.paul.billing_system.controller;

import com.paul.billing_system.dto.MedSpecialistDTO;
import com.paul.billing_system.entity.MedSpecialist;
import com.paul.billing_system.service.MedSpecialistServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.paul.billing_system.controller.AuthController.getErrorDetails;

@RestController
@RequestMapping("/medicalSpecialist")
@PreAuthorize("hasAuthority('ROLE_Admin')")
public class MedSpecialistController {
    private final MedSpecialistServices medSpecialistServices;

    public MedSpecialistController(MedSpecialistServices medSpecialistServices) {
        this.medSpecialistServices = medSpecialistServices;
    }

    @PostMapping("/addMedSpecialist/{id}")
    public ResponseEntity<?> saveMedSpecialist(@RequestBody MedSpecialistDTO medSpecialistDTO, @PathVariable Long id, BindingResult bindingResult){
        ResponseEntity<?> errorDetails = getErrorDetails(bindingResult);
        if (errorDetails != null) return errorDetails;
        MedSpecialistDTO medSpecialistDTO1 = MedSpecialistDTO.form(medSpecialistServices.saveMedSpecialist(id,medSpecialistDTO));
        return new ResponseEntity<>(medSpecialistDTO1, HttpStatus.OK);
    }
    @GetMapping("/getAllMedSpecialist")
    public ResponseEntity<?> getAllMedSpecialist(){
        List<MedSpecialist> medSpecialists = medSpecialistServices.getAllMedSpecialist();
        List<MedSpecialistDTO> medSpecialistDTOList = medSpecialists.stream().map(MedSpecialistDTO::form).toList();
        return new ResponseEntity<>(medSpecialistDTOList,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMedicalSpecialist(@PathVariable Long id){
        MedSpecialistDTO medSpecialistDTO = MedSpecialistDTO.form(medSpecialistServices.getMedicalSpecialist(id));
        return new ResponseEntity<>(medSpecialistDTO,HttpStatus.OK);
    }

}
