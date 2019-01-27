package com.companyhr.repository;


import com.companyhr.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    // List<Note> getAllNotes();

}
