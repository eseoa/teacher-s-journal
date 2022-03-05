package ru.arkady.journal.controllers;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.arkady.journal.entities.Journal;
import ru.arkady.journal.entities.Teacher;
import ru.arkady.journal.repositories.*;
import ru.arkady.journal.security.UserInfo;

import java.util.*;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/activities")
public class ActivitiesControllerV1 {

    private TeacherRepository teacherRepository;
    private JournalRepository journalRepository;

    @Autowired
    public ActivitiesControllerV1(TeacherRepository teacherRepository, JournalRepository journalRepository) {
        this.teacherRepository = teacherRepository;
        this.journalRepository = journalRepository;
    }

    @GetMapping()
    public String activities(Model model){
        Optional<Teacher> teacher = teacherRepository.findByPhone(UserInfo.getUsername());
        Set<String> journals = journalRepository.findAll().stream()
                .filter(journal -> journal.getTeacher().equals(teacher.get()))
                .map(journal -> journal.getJournalType().toString())
                .collect(Collectors.toSet());
        model.addAttribute("journals", journals);

        return "activities";
    }

}
