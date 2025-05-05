package dev.leandroschillreff.do_it.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.leandroschillreff.do_it.dto.NoteDTO;
import dev.leandroschillreff.do_it.dto.NoteResponseDTO;
import dev.leandroschillreff.do_it.service.AuthService;
import dev.leandroschillreff.do_it.service.NoteService;
import dev.leandroschillreff.do_it.util.ApiResponseBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/notes")
@Tag(name = "Notes", description = "Endpoints for managing user notes")
@SecurityRequirement(name = "Bearer Authentication")
public class NoteController {

        private final NoteService noteService;
        private final AuthService authService;

        public NoteController(NoteService noteService, AuthService authService) {
                this.noteService = noteService;
                this.authService = authService;
        }

        @GetMapping
        @Operation(summary = "Get all notes", description = "Returns all notes of the authenticated user", responses = {
                        @ApiResponse(responseCode = "200", description = "Notes retrieved successfully", content = @Content(array = @ArraySchema(schema = @Schema(implementation = NoteDTO.class))))
        })
        public ResponseEntity<Map<String, Object>> getAllNotes() {
                Long userId = authService.getCurrentUserId();
                List<NoteResponseDTO> notes = noteService.getAllNotesByUserId(userId);
                Map<String, Object> response = ApiResponseBuilder.buildSuccessResponse(
                                200,
                                "Notes retrieved successfully",
                                notes);
                return ResponseEntity.ok(response);
        }

        @GetMapping("/{noteId}")
        @Operation(summary = "Get note by ID", description = "Returns the details of a specific note", responses = {
                        @ApiResponse(responseCode = "200", description = "Note retrieved successfully", content = @Content(schema = @Schema(implementation = NoteDTO.class))),
                        @ApiResponse(responseCode = "404", description = "Note not found")
        })
        public ResponseEntity<Map<String, Object>> getNoteById(@PathVariable Long noteId) {
                Long userId = authService.getCurrentUserId();
                NoteResponseDTO note = noteService.getNoteById(noteId, userId);
                Map<String, Object> response = ApiResponseBuilder.buildSuccessResponse(
                                200,
                                "Note retrieved successfully",
                                note);
                return ResponseEntity.ok(response);
        }

        @PostMapping
        @Operation(summary = "Create a new note", description = "Creates a new note for the authenticated user", responses = {
                        @ApiResponse(responseCode = "201", description = "Note created successfully", content = @Content(schema = @Schema(implementation = NoteDTO.class))),
                        @ApiResponse(responseCode = "400", description = "Invalid input")
        })
        public ResponseEntity<Map<String, Object>> createNote(@Valid @RequestBody NoteDTO noteDTO) {
                Long userId = authService.getCurrentUserId();
                NoteResponseDTO createdNote = noteService.createNote(noteDTO, userId);
                Map<String, Object> response = ApiResponseBuilder.buildSuccessResponse(
                                201,
                                "Note created successfully",
                                createdNote);
                return new ResponseEntity<>(response, HttpStatus.CREATED);
        }

        @PutMapping("/{noteId}")
        @Operation(summary = "Update a note", description = "Updates an existing note", responses = {
                        @ApiResponse(responseCode = "200", description = "Note updated successfully", content = @Content(schema = @Schema(implementation = NoteDTO.class))),
                        @ApiResponse(responseCode = "400", description = "Invalid input"),
                        @ApiResponse(responseCode = "403", description = "Unauthorized access"),
                        @ApiResponse(responseCode = "404", description = "Note not found")
        })
        public ResponseEntity<Map<String, Object>> updateNote(
                        @PathVariable Long noteId,
                        @Valid @RequestBody NoteDTO noteDTO) {
                Long userId = authService.getCurrentUserId();
                NoteResponseDTO updatedNote = noteService.updateNote(noteId, noteDTO, userId);
                Map<String, Object> response = ApiResponseBuilder.buildSuccessResponse(
                                200,
                                "Note updated successfully",
                                updatedNote);
                return ResponseEntity.ok(response);
        }

        @DeleteMapping("/{noteId}")
        @Operation(summary = "Delete a note", description = "Deletes a specific note", responses = {
                        @ApiResponse(responseCode = "204", description = "Note deleted successfully"),
                        @ApiResponse(responseCode = "403", description = "Unauthorized access"),
                        @ApiResponse(responseCode = "404", description = "Note not found")
        })
        public ResponseEntity<Void> deleteNote(@PathVariable Long noteId) {
                Long userId = authService.getCurrentUserId();
                noteService.deleteNote(noteId, userId);
                return ResponseEntity.noContent().build();
        }

        @PatchMapping("/{noteId}/complete")
        @Operation(summary = "Mark note as completed", description = "Marks the note as completed and sets the completion timestamp")
        public ResponseEntity<Map<String, Object>> completeNote(@PathVariable Long noteId) {
                Long userId = authService.getCurrentUserId();
                NoteResponseDTO updatedNote = noteService.completeNote(noteId, userId);
                Map<String, Object> response = ApiResponseBuilder.buildSuccessResponse(
                                200,
                                "Note marked as completed",
                                updatedNote);
                return ResponseEntity.ok(response);
        }

        @PatchMapping("/{noteId}/uncomplete")
        @Operation(summary = "Unmark note as completed", description = "Unmarks the note as completed and removes the completion timestamp")
        public ResponseEntity<Map<String, Object>> uncompleteNote(@PathVariable Long noteId) {
                Long userId = authService.getCurrentUserId();
                NoteResponseDTO updatedNote = noteService.uncompleteNote(noteId, userId);
                Map<String, Object> response = ApiResponseBuilder.buildSuccessResponse(
                                200,
                                "Note unmarked as completed",
                                updatedNote);
                return ResponseEntity.ok(response);
        }
}
