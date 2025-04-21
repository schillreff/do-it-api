package dev.leandroschillreff.do_it.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoteDTO {
    @NotBlank(message = "Title cannot be blank")
    private String title;

    @NotBlank(message = "Description cannot be blank")
    private String description;
}