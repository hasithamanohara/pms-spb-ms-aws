package com.pm.patientservice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PartialPatientUpdateDTO {

    @Size(max = 100, message = "Name cannot exceed 100 characters")
    private String name;

    @Email(message = "Email should be valid")
    private String email;

    @Size(max = 100, message = "Address cannot exceed 100 characters")
    private String address;

    private String dateOfBirth;
}
