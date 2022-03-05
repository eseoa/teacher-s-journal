package ru.arkady.journal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.arkady.journal.entities.*;
import ru.arkady.journal.entities.SchoolClass;
import ru.arkady.journal.repositories.ClassRepository;
import ru.arkady.journal.repositories.JournalRepository;
import ru.arkady.journal.repositories.TeacherRepository;
import ru.arkady.journal.security.UserInfo;

import java.util.*;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/activities/classes-journals")
public class ClassesControllerV1 {

    private TeacherRepository teacherRepository;
    private JournalRepository journalRepository;
    private ClassRepository classRepository;

    @Autowired
    public ClassesControllerV1(TeacherRepository teacherRepository, JournalRepository journalRepository, ClassRepository classRepository) {
        this.teacherRepository = teacherRepository;
        this.journalRepository = journalRepository;
        this.classRepository = classRepository;
    }

    @GetMapping()
    public String journals(Model model){
        Map<String, Set<SchoolClass>> classesInParallel = new TreeMap<>();
        for(String parallel : getParallels()){
            classesInParallel.put(parallel, getClassesInParallel(parallel));
        }
        model.addAttribute("classes", classesInParallel);
        return "classes-journals";
    }

    private List<String> getParallels(){
        return getClasses()
                .stream()
                .map(schoolClass->schoolClass.getClassName().substring(0,1))
                .collect(Collectors.toList());
    }

    private List<SchoolClass> getClasses(){
        Optional<Teacher> teacher = teacherRepository.findByPhone(UserInfo.getUsername());
        List<Journal> journals = journalRepository.findByJournalTypeAndTeacher(Journal.JournalType.CLASS, teacher.get());
        return journals
                .stream()
                .map(Journal::getSchoolClass)
                .collect(Collectors.toList());
    }

    private Set<SchoolClass> getClassesInParallel(String parallel){
        return getClasses()
                .stream()
                .filter(schoolClass->schoolClass.getClassName().substring(0,1).equals(parallel))
                .collect(Collectors.toSet());
    }
}
