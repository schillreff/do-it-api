package dev.leandroschillreff.do_it.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {

    @NotBlank(message = "Email não pode estar em branco")
    @Email(message = "Email deve ser válido")
    private String email;

    @NotBlank(message = "Senha não pode estar em branco")
    private String password;
}