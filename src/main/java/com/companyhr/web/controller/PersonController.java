package com.companyhr.web.controller;

import com.companyhr.model.Note;
import com.companyhr.model.Person;
import com.companyhr.service.NoteService;
import com.companyhr.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PersonController {

    private NoteService noteService;

    @Autowired
    public PersonController(NoteService noteService) {
        this.noteService = noteService;
    }

    @RequestMapping(value = "/greet", method = RequestMethod.GET)
    public String homePage(Model model) {
        model.addAttribute("note", new Note());
        return "greeting";
    }

    @RequestMapping(value = "/note", method = RequestMethod.POST)
    public String addPagePerson(@ModelAttribute Note note, Model model) {
        noteService.createNote(note);
        model.addAttribute("persons", noteService.getAllNotes());
        return "result";
    }
}