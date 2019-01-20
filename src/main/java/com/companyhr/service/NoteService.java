package com.companyhr.service;

import com.companyhr.model.Note;

import java.util.List;

public interface NoteService {

    Note createNote(Note note);

    Note getNote(Long id);

    Note editNote(Note note);

    void deleteNote(Note note);

    void deleteNote(Long id);

    List<Note> getAllNotes();

}
