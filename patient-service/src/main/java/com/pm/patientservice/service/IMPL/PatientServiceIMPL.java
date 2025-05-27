package com.pm.patientservice.service.IMPL;

import com.pm.patientservice.dto.request.PartialPatientUpdateDTO;
import com.pm.patientservice.dto.request.PatientRequestDTO;
import com.pm.patientservice.dto.response.PatientResponseDTO;
import com.pm.patientservice.exception.EmailAlreadyExistsException;
import com.pm.patientservice.exception.UserNotFoundException;
import com.pm.patientservice.mapper.PatientMapper;
import com.pm.patientservice.model.Patient;
import com.pm.patientservice.repository.PatientRepository;
import com.pm.patientservice.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientServiceIMPL implements PatientService {

    private final PatientRepository patientRepository;

    @Override
    public List<PatientResponseDTO> getAllPatients() {
        List<Patient> patients = patientRepository.findAll();

        if (patients.isEmpty()) {
            return Collections.emptyList();
        }

        return patients.stream().map(PatientMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
        if (patientRepository.existsByEmail(patientRequestDTO.getEmail())) {
            throw new EmailAlreadyExistsException("User already exists for " + patientRequestDTO.getEmail() + ". try another email");
        }

        Patient newPatient = patientRepository.save(PatientMapper.toModel(patientRequestDTO));
        return PatientMapper.toDTO(newPatient);
    }

    @Override
    public PatientResponseDTO updatePatient(UUID id, PartialPatientUpdateDTO partialPatientUpdateDTO) {
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found for id " + id));

        if (partialPatientUpdateDTO.getName() != null && !partialPatientUpdateDTO.getName().isBlank()) {
            patient.setName(partialPatientUpdateDTO.getName());
        }

        if (partialPatientUpdateDTO.getEmail() != null &&
                !partialPatientUpdateDTO.getEmail().isBlank() &&
                !patientRepository.existsByEmail(partialPatientUpdateDTO.getEmail())
        ) {
            patient.setEmail(partialPatientUpdateDTO.getEmail());
        }
        if (partialPatientUpdateDTO.getAddress() != null && !partialPatientUpdateDTO.getAddress().isBlank()) {
            patient.setAddress(partialPatientUpdateDTO.getAddress());
        }
        if (partialPatientUpdateDTO.getDateOfBirth() != null && !partialPatientUpdateDTO.getDateOfBirth().isBlank()) {
            patient.setDateOfBirth(LocalDate.parse(partialPatientUpdateDTO.getDateOfBirth()));
        }

        return PatientMapper.toDTO(patientRepository.save(patient));
    }

    @Override
    public Void deletePatient(UUID id) {

        Patient patient = patientRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found for id " + id));

        if(patient != null){
            patientRepository.deleteById(id);
        }
        return null;
    }

}
