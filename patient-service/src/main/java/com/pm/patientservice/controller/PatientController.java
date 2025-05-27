package com.pm.patientservice.controller;

import com.pm.patientservice.dto.request.PartialPatientUpdateDTO;
import com.pm.patientservice.dto.request.PatientRequestDTO;
import com.pm.patientservice.dto.response.PatientResponseDTO;
import com.pm.patientservice.service.IMPL.PatientServiceIMPL;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patient")
@Tag(name = "Patient", description = "API fir managing Patient")
@RequiredArgsConstructor
public class PatientController {
    private final PatientServiceIMPL patientService;
//    swagger ui
//    http://localhost:4000/swagger-ui/index.html

//    swagger json
//    http://localhost:4000/v3/api-docs

    @Operation(summary = "Get all patients")
    @GetMapping("/getAllPatient")
    public ResponseEntity<List<PatientResponseDTO>> getAllPatients() {
        return ResponseEntity.ok().body(patientService.getAllPatients());
    }

    @Operation(summary = "Create a new patient")
    @PostMapping("/createPatient")
    public ResponseEntity<PatientResponseDTO> createPatient(@Valid @RequestBody PatientRequestDTO patientRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(patientService.createPatient(patientRequestDTO));
    }

    @Operation(summary = "Update patient details")
    @PatchMapping("/updatePatient")
    public ResponseEntity<PatientResponseDTO> updatePatient(@RequestParam("id") UUID id, @Valid @RequestBody PartialPatientUpdateDTO partialPatientUpdateDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(patientService.updatePatient(id, partialPatientUpdateDTO));
    }

    @Operation(summary = "Delete patient")
    @DeleteMapping("/deletePatient")
    public ResponseEntity<Void> deletePatient(@RequestParam("id") UUID id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(patientService.deletePatient(id));
    }
}
