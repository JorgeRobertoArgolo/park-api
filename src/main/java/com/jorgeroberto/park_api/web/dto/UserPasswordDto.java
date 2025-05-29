package com.jorgeroberto.park_api.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserPasswordDto {

    @NotBlank
    @Size(min = 6, max= 6, message = "A senha deve ter 6 caracteres")
    private String currentPassword;

    @NotBlank
    @Size(min = 6, max= 6, message = "A senha deve ter 6 caracteres")
    private String newPassword;

    @NotBlank
    @Size(min = 6, max= 6, message = "A senha deve ter 6 caracteres")
    private String confirmNewPassword;
}
