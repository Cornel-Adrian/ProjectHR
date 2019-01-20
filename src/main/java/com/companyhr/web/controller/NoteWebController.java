package com.companyhr.web.controller;

import com.companyhr.model.Note;
import com.companyhr.api.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/notes")
public class NoteWebController {

    @Autowired
    NoteRepository noteRepository;

    @GetMapping(value = "/add")
    public String createNote(@Valid @RequestBody Note note) {
        System.out.println("Intra in mizerie");
        noteRepository.save(note);
        return "notesAdd";
    }
    @GetMapping("/getAll")
    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

}
