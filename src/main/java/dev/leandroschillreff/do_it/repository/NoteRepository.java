package dev.leandroschillreff.do_it.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.leandroschillreff.do_it.entity.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findAllByUserId(Long userId);

    Optional<Note> findByIdAndUserId(Long id, Long userId);
}