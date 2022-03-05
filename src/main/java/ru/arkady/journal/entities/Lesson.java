package ru.arkady.journal.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "lessons")
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lesson_id")
    private int lessonId;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "class_id", referencedColumnName = "class_id")
    private SchoolClass schoolClass;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "teacher_id", referencedColumnName = "teacher_id")
    private Teacher teacher;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "subject_id", referencedColumnName = "subject_id")
    private Subject subject;
    @Column(name = "date")
    private Date date;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "lesson_id", referencedColumnName = "lesson_id")
    private List<Grade> grades;



}
