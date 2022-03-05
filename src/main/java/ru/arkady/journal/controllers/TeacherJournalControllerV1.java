package ru.arkady.journal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.arkady.journal.entities.*;
import ru.arkady.journal.entities.SchoolClass;
import ru.arkady.journal.repositories.*;
import ru.arkady.journal.security.UserInfo;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@Controller
@RequestMapping("/activities/classes-journals/teacher's-journal")
public class TeacherJournalControllerV1 {

    private SubjectRepository subjectRepository;
    private ClassRepository classRepository;
    private StudentRepository studentRepository;
    private LessonRepository lessonRepository;
    private GradeRepository gradeRepository;
    private TeacherRepository teacherRepository;
    private JournalRepository journalRepository;

    @Autowired
    public TeacherJournalControllerV1(SubjectRepository subjectRepository,
                                      ClassRepository classRepository,
                                      StudentRepository studentRepository,
                                      LessonRepository lessonRepository,
                                      GradeRepository gradeRepository,
                                      TeacherRepository teacherRepository,
                                      JournalRepository journalRepository)
    {
        this.subjectRepository = subjectRepository;
        this.classRepository = classRepository;
        this.studentRepository = studentRepository;
        this.lessonRepository = lessonRepository;
        this.gradeRepository = gradeRepository;
        this.teacherRepository = teacherRepository;
        this.journalRepository = journalRepository;
    }

    private int classId;
    private int subjectId;
    private int lessonId;
    private int studentId;

    @GetMapping()
    public String journal(HttpServletRequest servletRequest,
                          Model model){

        if(!requestCheck(servletRequest)){
            return "redirect:/activities/classes-journals";
        }

        this.classId = Integer.parseInt(servletRequest.getParameter("classId"));
        this.subjectId = Integer.parseInt(servletRequest.getParameter("subjectId"));

        if(!teacherCheck(classId)){
            return "redirect:/activities";
        }
        List<Student> students = studentRepository.findBySchoolClass(classRepository.findByClassId(classId).get());
        List<Lesson> lessons = lessonRepository.findBySchoolClassAndSubject(classRepository.findByClassId(classId).get(),
                subjectRepository.findBySubjectId(subjectId).get());
        students.sort(Comparator.comparing(Student::getName));

        Map<Lesson, List<Grade>> lessonGradesMap = new TreeMap<>(Comparator.comparing(Lesson::getLessonId));
        for(Lesson lesson : lessons){
            lessonGradesMap.put(lesson, lesson.getGrades());
        }
        model.addAttribute("studentsList", students);
        model.addAttribute("lessonGradesMap", lessonGradesMap);
        return "teachers-journal";
    }

    @PostMapping()
    public String createLesson(@ModelAttribute("lesson") Lesson lesson){
        String redirect = "redirect:/activities/classes-journals/teacher's-journal?classId=" +
                classId + "&subjectId=" + subjectId;
        lesson.setDate(Date.valueOf(LocalDate.now()));
        lesson.setSchoolClass(classRepository.findByClassId(classId).get());
        lesson.setTeacher(teacherRepository.findByTeacherId(1).get());
        lesson.setSubject(subjectRepository.findBySubjectId(subjectId).get());
        lessonRepository.save(lesson);
        return redirect;
    }

    @GetMapping("/lesson-{id}")
    public String lesson(@PathVariable("id") int id, Model model){
        if(!lessonRepository.findByLessonId(id).isPresent()){
            return "redirect:/activities" ;
        }
        Lesson lesson = lessonRepository.findByLessonId(id).get();
        if(!teacherCheck(lesson.getSchoolClass().getClassId())){
            return "redirect:/activities";
        }
        List<Student> students = studentRepository.findBySchoolClass(lesson.getSchoolClass());
        Map<Student, Grade> studentGradeMap = new TreeMap<>(Comparator.comparing(Student::getName));
        students.sort(Comparator.comparing(Student::getName));
        for(Grade grade : lesson.getGrades()){
            studentGradeMap.put(grade.getStudent(), grade);
        }
        model.addAttribute("lesson",lesson);
        model.addAttribute("students",students);
        model.addAttribute("studentGrade",studentGradeMap);
        subjectId = lesson.getSubject().getSubjectId();
        classId = lesson.getSchoolClass().getClassId();
        lessonId = id;
        return "lesson";
    }

    @GetMapping("/newGrade")
    public String createGrade(HttpServletRequest servletRequest,
                              Model model){
        if(servletRequest.getParameter("studentId") == null || servletRequest.getParameter("studentId").equals("") || lessonId==0){
            return "redirect:/activities/classes-journals";
        }
        int studentId=Integer.parseInt(servletRequest.getParameter("studentId"));
        if(!teacherCheck(studentRepository.findByStudentId(studentId).getSchoolClass().getClassId())){
            return "redirect:/activities";
        }
        this.studentId=studentId;
        model.addAttribute("grade",new Grade());
        return "newGrade";
    }

    @PostMapping("/newGrade")
    public String createGrade(@ModelAttribute("grade") Grade grade){
        grade.setStudent(studentRepository.findByStudentId(studentId));
        grade.setLesson(lessonRepository.findByLessonId(lessonId).get());
        gradeRepository.save(grade);
        return "redirect:/activities/classes-journals/teacher's-journal/lesson-" + lessonId;
    }

    @GetMapping("/grade-{id}")
    public String grade(@PathVariable("id") int id, Model model){
        if(!gradeRepository.findByGradeId(id).isPresent()){
            return "redirect:/activities" ;
        }
        model.addAttribute("grade", gradeRepository.findByGradeId(id).get());
        return "test";
    }

    @PatchMapping("/grade-{id}")
    public String updateGrade(@ModelAttribute("grade") Grade grade, @PathVariable("id") int id) {
        gradeRepository.findByGradeId(id).get().setGrade(grade.getGrade());
        gradeRepository.save(gradeRepository.findByGradeId(id).get());
        return "redirect:/activities/classes-journals/teacher's-journal/lesson-" + lessonId;
    }

    @DeleteMapping("/grade-{id}")
    public String deleteGrade(@PathVariable("id") int id){
        if(!gradeRepository.findByGradeId(id).isPresent()){
            return "redirect:/activities" ;
        }
        gradeRepository.deleteByGradeId(id);
        return "redirect:/activities/classes-journals/teacher's-journal/lesson-" + lessonId;
    }

    private boolean requestCheck(HttpServletRequest servletRequest){

        if(servletRequest.getParameter("classId") == null || servletRequest.getParameter("subjectId") == null){
            return false;
        }
        if(servletRequest.getParameter("classId").equals("") || servletRequest.getParameter("subjectId").equals("")){
            return false;
        }
        return true;

    }

    private boolean teacherCheck(int classId){
        Teacher teacher = teacherRepository.findByPhone(UserInfo.getUsername()).get();
        SchoolClass schoolClass = classRepository.findByClassId(classId).get();
        return journalRepository.findBySchoolClassAndTeacherAndJournalType(schoolClass, teacher, Journal.JournalType.CLASS).isPresent();
    }

}
