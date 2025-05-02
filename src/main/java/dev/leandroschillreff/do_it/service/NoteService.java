package dev.leandroschillreff.do_it.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import dev.leandroschillreff.do_it.dto.NoteDTO;
import dev.leandroschillreff.do_it.dto.NoteResponseDTO;
import dev.leandroschillreff.do_it.entity.Note;
import dev.leandroschillreff.do_it.entity.User;
import dev.leandroschillreff.do_it.exception.ResourceNotFoundException;
import dev.leandroschillreff.do_it.exception.UnauthorizedAccessException;
import dev.leandroschillreff.do_it.repository.NoteRepository;
import dev.leandroschillreff.do_it.repository.UserRepository;

@Service
public class NoteService {

    private static final String NOTE_NOT_FOUND = "Note not found with id: ";

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    public NoteService(NoteRepository noteRepository, UserRepository userRepository) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
    }

    public List<NoteResponseDTO> getAllNotesByUserId(Long userId) {
        return noteRepository.findAllByUserId(userId)
                .stream()
                .map(this::convertToResponseDTO)
                .toList();
    }

    public NoteResponseDTO getNoteById(Long noteId, Long userId) {
        Note note = noteRepository.findByIdAndUserId(noteId, userId)
                .orElseThrow(() -> new ResourceNotFoundException(NOTE_NOT_FOUND + noteId));

        if (!note.getUser().getId().equals(userId)) {
            throw new UnauthorizedAccessException("You do not have permission to access this note.");
        }

        return convertToResponseDTO(note);
    }

    public NoteResponseDTO createNote(NoteDTO noteDTO, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        Note note = new Note();
        note.setTitle(noteDTO.getTitle());
        note.setDescription(noteDTO.getDescription());
        note.setUser(user);

        Note savedNote = noteRepository.save(note);
        return convertToResponseDTO(savedNote);
    }

    public NoteResponseDTO updateNote(Long noteId, NoteDTO noteDTO, Long userId) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException(NOTE_NOT_FOUND + noteId));

        if (!note.getUser().getId().equals(userId)) {
            throw new UnauthorizedAccessException("You do not have permission to update this note.");
        }

        note.setTitle(noteDTO.getTitle());
        note.setDescription(noteDTO.getDescription());

        Note updatedNote = noteRepository.save(note);
        return convertToResponseDTO(updatedNote);
    }

    public void deleteNote(Long noteId, Long userId) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException(NOTE_NOT_FOUND + noteId));

        if (!note.getUser().getId().equals(userId)) {
            throw new UnauthorizedAccessException("You do not have permission to delete this note.");
        }

        noteRepository.delete(note);
    }

    public NoteResponseDTO completeNote(Long noteId, Long userId) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException(NOTE_NOT_FOUND + noteId));

        if (!note.getUser().getId().equals(userId)) {
            throw new UnauthorizedAccessException("You do not have permission to complete this note.");
        }

        note.setCompleted(true);
        note.setCompletedAt(LocalDateTime.now());

        Note updatedNote = noteRepository.save(note);
        return convertToResponseDTO(updatedNote);
    }

    public NoteResponseDTO uncompleteNote(Long noteId, Long userId) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException(NOTE_NOT_FOUND + noteId));

        if (!note.getUser().getId().equals(userId)) {
            throw new UnauthorizedAccessException("You do not have permission to modify this note.");
        }

        note.setCompleted(false);
        note.setCompletedAt(null);

        Note updatedNote = noteRepository.save(note);
        return convertToResponseDTO(updatedNote);
    }

    private NoteResponseDTO convertToResponseDTO(Note note) {
        NoteResponseDTO noteResponseDTO = new NoteResponseDTO();
        noteResponseDTO.setId(note.getId());
        noteResponseDTO.setTitle(note.getTitle());
        noteResponseDTO.setDescription(note.getDescription());
        noteResponseDTO.setCompleted(note.isCompleted());
        noteResponseDTO.setCompletedAt(note.getCompletedAt());
        noteResponseDTO.setCreatedAt(note.getCreatedAt());
        noteResponseDTO.setUpdatedAt(note.getUpdatedAt());
        return noteResponseDTO;
    }
}