package com.companyhr.service;
import com.companyhr.model.Note;
import com.companyhr.api.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {

    @Autowired
    private NoteRepository noteRepository;

    @Override
    public Note createNote(Note note) {
        return noteRepository.save(note);
    }

    @Override
    public Note getNote(Long id) {
        return new Note();
    }

    @Override
    public Note editNote(Note note) {
        return noteRepository.save(note);
    }

    @Override
    public void deleteNote(Note note) {
        noteRepository.delete(note);
    }

    @Override
    public void deleteNote(Long id) {
        System.out.println("blah");
    }


    @Override
    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }
}
