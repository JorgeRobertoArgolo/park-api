package com.jorgeroberto.park_api.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserLoginDto {
    @NotBlank
    @Email(message = "Formato de email inválido!")
    private String username;

    @NotBlank
    @Size(min = 6, max= 6, message = "A senha deve ter 6 caracteres")
    private String password;
}
