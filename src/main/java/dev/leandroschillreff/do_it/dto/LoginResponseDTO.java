package dev.leandroschillreff.do_it.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {
    private String timestamp;
    private String token;
    private Long userId;
    private String userName;
    private String userEmail;
}