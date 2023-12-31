package com.paul.billing_system.controller;

import com.paul.billing_system.dto.UserInfoDTO;
import com.paul.billing_system.service.UserServices;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/superAdmin")
public class SuperAdminController {
    private final UserServices userServices;

    public SuperAdminController(UserServices userServices) {
        this.userServices = userServices;
    }

    @PostMapping("/addSuperAdmin")
    public ResponseEntity<?> save(@Valid @RequestBody UserInfoDTO userInfoDTO, BindingResult bindingResult) {
        UserInfoDTO userInfoDTO1 = UserInfoDTO.form(userServices.saveSuperAdmin(userInfoDTO));
        return new ResponseEntity<>(userInfoDTO1, HttpStatus.CREATED);
    }
}
