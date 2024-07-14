package dev.mananhemani.markethub.DTOs.Security;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {

    @NotBlank
    private String username;

    @NotBlank
    @Size(min = 8 , max = 40)
    private String password;

    @Email
    private String email;

    private Set<String> role;

}
