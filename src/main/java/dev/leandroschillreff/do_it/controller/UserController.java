package dev.leandroschillreff.do_it.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.leandroschillreff.do_it.dto.UserDTO;
import dev.leandroschillreff.do_it.dto.UserResponseDTO;
import dev.leandroschillreff.do_it.service.AuthService;
import dev.leandroschillreff.do_it.service.UserService;
import dev.leandroschillreff.do_it.util.ApiResponseBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "Endpoints for user management")
@SecurityRequirement(name = "Bearer Authentication")
public class UserController {

    private final AuthService authService;
    private final UserService userService;

    public UserController(UserService userService, AuthService authService) {
        this.authService = authService;
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Get user by ID", description = "Returns the details of a specific user (only the user themself can access)", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "403", description = "Unauthorized access"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<Map<String, Object>> getUserById(@PathVariable Long userId) {
        Long currentUserId = authService.getCurrentUserId();
        UserResponseDTO userDTO = userService.getUserById(userId, currentUserId);
        Map<String, Object> response = ApiResponseBuilder.buildSuccessResponse(
                200,
                "User retrieved successfully",
                userDTO);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{userId}")
    @Operation(summary = "Update user", description = "Updates the data of a specific user (only the user themself can update)", responses = {
            @ApiResponse(responseCode = "200", description = "User updated successfully", content = @Content(schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "403", description = "Unauthorized access"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "409", description = "Email already in use")
    })
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long userId,
            @Valid @RequestBody UserDTO userDTO) {
        Long currentUserId = authService.getCurrentUserId();
        UserResponseDTO updatedUser = userService.updateUser(userId, userDTO, currentUserId);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "Delete user", description = "Deletes a specific user (only the user themself can delete their account)", responses = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "403", description = "Unauthorized access"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable Long userId) {
        Long currentUserId = authService.getCurrentUserId();
        userService.deleteUser(userId, currentUserId);
        return ResponseEntity.noContent().build();
    }
}