package dev.leandroschillreff.do_it.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoteDTO {
    @NotBlank(message = "Título não pode estar em branco")
    private String title;

    @NotBlank(message = "Descrição não pode estar em branco")
    private String description;
}