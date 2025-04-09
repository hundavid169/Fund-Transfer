package com.fund.fund_transfer.dto.request;

import com.fund.fund_transfer.validation.constraint.UniqueEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequest {
    @NotBlank(message = "First Name is required")
    @Size(min = 2, max = 50, message = "First Name must be between 2 and 50 characters")
    private String firstName;

    @NotBlank(message = "Last Name is required")
    @Size(min = 2, max = 50, message = "Last Name must be between 2 and 50 characters")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @UniqueEmail
    private String email;

    @NotBlank(message = "Telephone is required")
    private String tel;
    @NotBlank(message = "Id Card is required")
    private String idCard;
    private String address;
    @NotBlank(message = "Password is required")
    private String password;

}
